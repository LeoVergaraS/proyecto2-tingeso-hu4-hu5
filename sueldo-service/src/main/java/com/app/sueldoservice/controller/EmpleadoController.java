package com.app.sueldoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.sueldoservice.entity.Empleado;
import com.app.sueldoservice.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/mostrar")
    public ResponseEntity<List<Empleado>> getAll(){
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        if(empleados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getOne(Long id){
        Empleado empleado = empleadoService.obtenerEmpleadoPorRut(id);
        if(empleado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleado);
    }

    @PostMapping("/crear")
    public ResponseEntity<Empleado> create(@RequestBody Empleado empleado){
        Empleado empleadoCreado = empleadoService.crearEmpleado(empleado);
        return ResponseEntity.ok(empleadoCreado);
    }
}
