package com.app.sueldoservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.sueldoservice.entity.Sueldo;
import com.app.sueldoservice.entity.Empleado;

@Service
public class RRHHservice {

    @Autowired 
    SueldoService sueldoService;

    @Autowired
    EmpleadoService empleadoService;

    RestTemplate restTemplate = new RestTemplate();

    private int sueldoMensualFijoEmpleado(Empleado empleado){
        if(empleado.getCategoria() == 'A'){
            return 1700000;
        }else if(empleado.getCategoria() == 'B'){
            return 1200000;
        }else{
            return 800000;
        }
    }

    private double montoHorasExtrasEmpleado(Empleado empleado, double horasExtras){
        if(empleado.getCategoria() == 'A'){
            return horasExtras * 25000;
        }else if(empleado.getCategoria() == 'B'){
            return horasExtras * 20000;
        }else{
            return horasExtras * 10000;
        }
    }

    private double descuentosPrevisional(double sueldoFinal){
        return sueldoFinal * 0.1;
    }

    private double descuentoSalud(double sueldoFinal){
        return sueldoFinal * 0.08;
    }

    private double bonificacionTiempoServicio(Empleado empleado, int sueldoMensualFijo){
        int anioIngreso =  Integer.valueOf(empleado.getFechaIngreso().toString().split("-")[0]);
        int anioActual = LocalDateTime.now().getYear();
        int anioServicio = anioActual - anioIngreso;

        if(anioServicio >= 25){return Math.round(sueldoMensualFijo * 0.17);}
        else if(anioServicio >= 20){return Math.round(sueldoMensualFijo * 0.14);}
        else if(anioServicio >= 15){return Math.round(sueldoMensualFijo * 0.11);}
        else if(anioServicio >= 10){return Math.round(sueldoMensualFijo * 0.08);}
        else if(anioServicio >= 5){return Math.round(sueldoMensualFijo * 0.05);}
        else{return 0;}
    }

    private double descuentoPorAtraso(List<Integer> tiempoAtraso, int sueldoMensualFijo){
        double monto10 = tiempoAtraso.get(0) * 0.01 * sueldoMensualFijo;
        double monto25 = tiempoAtraso.get(1) * 0.03 * sueldoMensualFijo;
        double monto45 = tiempoAtraso.get(2) * 0.06 * sueldoMensualFijo;
        double monto70 = tiempoAtraso.get(3) * 0.15 * sueldoMensualFijo;
        return monto10 + monto25 + monto45 + monto70;
    }

    private List<Integer> juntarAtrasos(Integer[] atrasos, Integer inasistencia){
        List<Integer> atrasosInasistencia = new ArrayList<>();
        for(int i = 0; i < atrasos.length; i++){
            atrasosInasistencia.add(atrasos[i]);
        }
        atrasosInasistencia.add(inasistencia);
        return atrasosInasistencia;
    }
    
    public boolean calcularPlanilla(int mes, int anio){
        sueldoService.eliminarSueldo();
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        if(empleados == null){return false;}
        for(Empleado e:empleados){
            Integer[] ta = restTemplate.getForObject("http://localhost:8005/atrasos/verificar/{mes}/{anio}/{rut}", Integer[].class, mes, anio, e.getRut());
            if(ta == null){return false;}
            Double horaExtra = restTemplate.getForObject("http://localhost:8003/horaExtra/verificar/{mes}/{anio}/{rut}", Double.class, mes, anio, e.getRut());
            if(horaExtra == null){return false;}
            Integer cantInasistencia = restTemplate.getForObject("http://localhost:8002/inasistencia/verificar/{mes}/{anio}/{rut}", Integer.class, mes, anio, e.getRut());
            List<Integer> tiempoAtraso = juntarAtrasos(ta, cantInasistencia);
            int sueldoMensualFijo = sueldoMensualFijoEmpleado(e);
            Double bonificacionTiempoServicio = bonificacionTiempoServicio(e, sueldoMensualFijo);
            Double pagoHorasExtras = montoHorasExtrasEmpleado(e, horaExtra);
            Double descuentosAtrasos = descuentoPorAtraso(tiempoAtraso, sueldoMensualFijo);
            Double sueldoBruto = sueldoMensualFijo + bonificacionTiempoServicio + pagoHorasExtras - descuentosAtrasos;
            Double descuentoPrevisional = descuentosPrevisional(sueldoBruto);
            Double descuentoSalud = descuentoSalud(sueldoBruto);
            Double sueldoFinal = sueldoBruto - descuentoPrevisional - descuentoSalud;
            Sueldo sueldo = new Sueldo(null, e.getRut(), sueldoMensualFijo, bonificacionTiempoServicio, pagoHorasExtras, descuentosAtrasos, sueldoBruto, descuentoPrevisional, descuentoSalud, sueldoFinal, mes, anio);
            sueldoService.crearSueldo(sueldo);
        }
        return true;
    }
}