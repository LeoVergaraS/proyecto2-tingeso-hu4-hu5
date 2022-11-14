package com.app.sueldoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.sueldoservice.entity.Sueldo;
import com.app.sueldoservice.service.SueldoService;

@RestController
@RequestMapping("/sueldo")
public class SueldoController {
    
    @Autowired
    SueldoService sueldoService;

    @GetMapping
    public ResponseEntity<List<Sueldo>> getAll(){
        List<Sueldo> sueldos = sueldoService.obtenerSueldos();
        if(sueldos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sueldos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sueldo> getById(@PathVariable Long id){
        Sueldo sueldo = sueldoService.obtenerSueldo(id);
        if(sueldo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sueldo);
    }

    @PostMapping
    public ResponseEntity<Sueldo> create(@RequestBody Sueldo sueldo){
        Sueldo sueldoCreado = sueldoService.crearSueldo(sueldo);
        return ResponseEntity.ok(sueldoCreado);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(){
        sueldoService.eliminarSueldo();
        return ResponseEntity.ok(null);
    }

}
