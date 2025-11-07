package com.controlacademico.modelo;

public class Curso {
    private int cursoId;
    private String nombreCurso;
    private int periodoAcademicoId;
    private int docenteId;
    private String descripcionCurso;

    // Constructor vacío
    public Curso() {}

    // Constructor completo
    public Curso(int cursoId, String nombreCurso, int periodoAcademicoId, int docenteId, String descripcionCurso) {
        this.cursoId = cursoId;
        this.nombreCurso = nombreCurso;
        this.periodoAcademicoId = periodoAcademicoId;
        this.docenteId = docenteId;
        this.descripcionCurso = descripcionCurso;
    }

    // Getters y Setters
    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public int getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(int periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }

    public int getDocenteId() { return docenteId; }
    public void setDocenteId(int docenteId) { this.docenteId = docenteId; }

    public String getDescripcionCurso() { return descripcionCurso; }
    public void setDescripcionCurso(String descripcionCurso) { this.descripcionCurso = descripcionCurso; }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + cursoId +
                ", nombre='" + nombreCurso + '\'' +
                ", periodoId=" + periodoAcademicoId +
                ", docenteId=" + docenteId +               
                ", descripcion='" + descripcionCurso + '\'' +
                
                '}';
    }
}
    