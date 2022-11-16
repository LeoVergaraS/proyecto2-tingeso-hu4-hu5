package com.app.sueldoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.sueldoservice.entity.Sueldo;
import com.app.sueldoservice.service.RRHHservice;
import com.app.sueldoservice.service.SueldoService;

@RestController
@RequestMapping("/sueldo")
public class SueldoController {
    
    @Autowired
    SueldoService sueldoService;

    @Autowired
    RRHHservice rrhhService;

    @GetMapping
    public ResponseEntity<List<Sueldo>> getAll(){
        List<Sueldo> sueldos = sueldoService.obtenerSueldos();
        if(sueldos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sueldos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sueldo> getById(@PathVariable("id") Long id){
        Sueldo sueldo = sueldoService.obtenerSueldo(id);
        if(sueldo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sueldo);
    }

    @PostMapping("/calcular/{mes}/{anio}")
    public ResponseEntity<String> calcularSueldo(@PathVariable("mes") int mes, @PathVariable("anio") int anio){
        Boolean creado = rrhhService.calcularPlanilla(mes, anio);
        if(creado){
            return ResponseEntity.ok("Se crearon los sueldos");
        }
        return ResponseEntity.ok("No hay empleados para calcular su sueldo");
    }

}
