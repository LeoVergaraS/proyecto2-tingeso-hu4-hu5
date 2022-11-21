package com.app.sueldoservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.sueldoservice.entity.Sueldo;
import com.app.sueldoservice.models.Planilla;

@Repository
public interface SueldoRepository extends JpaRepository<Sueldo, Long>{
    @Query(value = "SELECT s.rut, CONCAT(e.nombre, \" \", e.apellido) as nombre_completo,"+
                    " e.categoria, year(sysdate())-year(e.fecha_ingreso) as anios_servicio,"+
                    " s.sueldo_fijo_mensual, s.bonificacion_tiempo_servicio, s.pago_horas_extras,"+
                    " s.descuentos_atrasos, s.sueldo_bruto, s.descuentos_previsional,"+
                    " s.descuentos_salud, s.sueldo_final FROM sueldo s, empleados e WHERE s.rut = e.rut;",
                    nativeQuery = true)
    List<Planilla> getPlanillas();
}
