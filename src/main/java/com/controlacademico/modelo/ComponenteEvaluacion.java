package com.controlacademico.modelo;

public class ComponenteEvaluacion {
    private int componente_evaluacionId;
    private int corteEvaluacionId;
    private String nombreComponente;
    private double porcentaje;

    // Getters y Setters
    public int getComponenteEvaluacionId() {
        return componente_evaluacionId;
    }

    public void setComponenteEvaluacionId(int componente_evaluacionId) {
        this.componente_evaluacionId = componente_evaluacionId;
    }
    public int getCorteEvaluacionId() {
        return corteEvaluacionId;
    }
    public void setCorteEvaluacionId(int corteEvaluacionId) {
        this.corteEvaluacionId = corteEvaluacionId;
    }
    public String getNombreComponente() {
        return nombreComponente;
    }
    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }
    public double getPorcentaje() {
        return porcentaje;
    }   
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
