package com.app.sueldoservice.models;

public interface Planilla{
    String getRut();
    String getNombre_completo();
    char getCategoria();
    Integer getAnios_servicio();
    Integer getSueldo_fijo_mensual();
    Double getBonificacion_tiempo_servicio();
    Double getPago_horas_extras();
    Double getDescuentos_atrasos();
    Double getSueldo_bruto();
    Double getDescuentos_previsional();
    Double getDescuentos_salud();
    Double getSueldo_final();
}
