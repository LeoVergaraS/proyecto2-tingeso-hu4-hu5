package com.app.sueldoservice.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    
    private String rut;
    private String nombre;
    private String apellido;
    private char categoria;
    private Date fechaNacimiento;
    private Date fechaIngreso;
}
