package com.app.sueldoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.sueldoservice.entity.Sueldo;
import com.app.sueldoservice.repository.SueldoRepository;

@Service
public class SueldoService {
    
    @Autowired
    SueldoRepository sueldoRepository;

    public List<Sueldo> obtenerSueldos(){
        return sueldoRepository.findAll();
    } 

    public Sueldo obtenerSueldo(Long id){
        return sueldoRepository.findById(id).orElse(null);
    }

    public Sueldo crearSueldo(Sueldo sueldo){
        Sueldo sueldoCreado = sueldoRepository.save(sueldo);
        return sueldoCreado;
    }

    public void eliminarSueldo(){
        sueldoRepository.deleteAll();
    }
}
