package com.controlacademico.modelo;

import java.sql.Date;

public class Asistencia {
    private int asistenciaId;
    private int estudianteId;
    private int cursoId;
    private Date fechaClase;
    private String estadoAsistencia; // presente, ausente o tardanza
    private String novedades;

    // Getters y Setters
    public int getAsistenciaId() {
        return asistenciaId;
    }
    public void setAsistenciaId(int asistenciaId) {
        this.asistenciaId = asistenciaId;
    }

    public int getEstudianteId() {
        return estudianteId;
    }
    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public int getCursoId() {
        return cursoId;
    }
    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public Date getFechaClase() {
        return fechaClase;
    }
    public void setFechaClase(Date fechaClase) {
        this.fechaClase = fechaClase;
    }

    public String getEstadoAsistencia() {
        return estadoAsistencia;
    }
    public void setEstadoAsistencia(String estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

    public String getNovedades() {
        return novedades;
    }
    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }
}
