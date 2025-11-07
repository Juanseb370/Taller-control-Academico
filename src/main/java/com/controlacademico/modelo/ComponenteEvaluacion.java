package com.controlacademico.modelo;

public class ComponenteEvaluacion {
    private int componenteEvaluacionId;
    private int corteEvaluacionId;
    private String nombreComponente;
    private double porcentaje;

    // Getters y Setters
    public int getComponenteEvaluacionId() {
        return componenteEvaluacionId;
    }

    public void setComponenteEvaluacionId(int componenteEvaluacionId) {
        this.componenteEvaluacionId = componenteEvaluacionId;
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
