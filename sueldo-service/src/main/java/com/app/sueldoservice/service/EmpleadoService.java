package com.app.sueldoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.sueldoservice.entity.Empleado;
import com.app.sueldoservice.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado obtenerEmpleadoPorRut(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public Empleado crearEmpleado(Empleado empleado) {
        Empleado empleadoCreado = empleadoRepository.save(empleado);
        return empleadoCreado;
    }
}
