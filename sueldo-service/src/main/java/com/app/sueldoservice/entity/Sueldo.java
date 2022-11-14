package com.app.sueldoservice.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sueldo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sueldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private int sueldoFijoMensual;
    private double bonificacionTiempoServicio;
    private double pagoHorasExtras;
    private double descuentosAtrasos;
    private double sueldoBruto;
    private double descuentosPrevisional;
    private double descuentosSalud;
    private double sueldoFinal;
    private int mes;
    private int anio;
}
