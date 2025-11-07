package com.controlacademico.modelo;

public class Estudiante {
    private int estudianteId;
    private String identificacion;
    private String nombre;
    private String correoInstitucional;
    private String telefono;
    private String correoPersonal;
    private boolean esVocero;
    private String comentarios;
    private String tipoDocumento;
    private String genero;


    // Constructor vacío
    public Estudiante() {}

    // Constructor completo
    public Estudiante(int estudianteId, String identificacion, String nombre, String correoInstitucional, String telefono, String correoPersonal, boolean esVocero, String comentarios, String tipoDocumento, String genero) {
        this.estudianteId = estudianteId;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correoInstitucional = correoInstitucional;
        this.telefono = telefono;
        this.correoPersonal = correoPersonal;
        this.esVocero = esVocero;
        this.comentarios = comentarios;
        this.tipoDocumento = tipoDocumento;
        this.genero = genero;

    }

    // Getters y Setters
    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreoInstitucional() { return correoInstitucional; }
    public void setCorreoInstitucional(String correoInstitucional) { this.correoInstitucional = correoInstitucional; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoPersonal() { return correoPersonal; }
    public void setCorreoPersonal(String correoPersonal) { this.correoPersonal = correoPersonal; }
    
    public boolean isEsVocero() { return esVocero; }
    public void setEsVocero(boolean esVocero) { this.esVocero = esVocero; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }



    // @Override
    // public String toString() {
    //     return "Estudiante{" +
    //             "id=" + estudianteId +
    //             ", nombre='" + nombre + '\'' +
    //             ", correo='" + correoInstitucional + '\'' +
    //             ", telefono='" + telefono + '\'' +
    //             ", correoPersonal='" + correoPersonal + '\'' +
    //             ", esVocero=" + esVocero +
    //             ", comentarios='" + comentarios + '\'' +
    //             ", tipoDocumento='" + tipoDocumento + '\'' +
    //             ", genero='" + genero + '\'' +
                

    //             '}';
    // }
}
