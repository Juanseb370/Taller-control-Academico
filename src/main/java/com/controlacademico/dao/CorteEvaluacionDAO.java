package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.CorteEvaluacion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CorteEvaluacionDAO {

    public boolean insertarCorteEvaluacion(CorteEvaluacion corte) {
        String sql = "INSERT INTO cortes_evaluacion (curso_id, periodo_academico_id, nombre_corte, porcentaje, comentarios_corte) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, corte.getCursoId());
            ps.setInt(2, corte.getPeriodoAcademicoId());   
            ps.setString(3, corte.getNombreCorte());
            ps.setDouble(4, corte.getPorcentaje());
            ps.setString(5, corte.getComentariosCorte());



            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el corte de evaluación: " + e.getMessage());
            return false;
        }
    }
}
