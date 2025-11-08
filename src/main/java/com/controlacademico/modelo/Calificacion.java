package com.controlacademico.modelo;

public class Calificacion {
    private int calificacionId;
    private int estudianteId;
    private int componenteEvaluacionId;
    private double nota;
    private String comentariosCalificacion;

    // Getters y setters
    public int getCalificacionId() { return calificacionId; }
    public void setCalificacionId(int calificacionId) { this.calificacionId = calificacionId; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getComponenteEvaluacionId() { return componenteEvaluacionId; }
    public void setComponenteEvaluacionId(int componenteEvaluacionId) { this.componenteEvaluacionId = componenteEvaluacionId; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public String getComentariosCalificacion() { return comentariosCalificacion; }
    public void setComentariosCalificacion(String comentariosCalificacion) { this.comentariosCalificacion = comentariosCalificacion; }
}
    