package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.CorteEvaluacion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CorteEvaluacionDAO {

    public int insertarCorteEvaluacionYObtenerId(CorteEvaluacion corte) {
        String sql = "INSERT INTO cortes_evaluacion (curso_id, nombre_corte, porcentaje) VALUES (?, ?, ?)";
        int idGeneradoC = -1;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, corte.getCursoId());
            ps.setString(2, corte.getNombreCorte());
            ps.setDouble(3, corte.getPorcentaje());

            ps.executeUpdate();
           
        
// Obtener el ID generado
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idGeneradoC = rs.getInt(1);
            System.out.println("✅ ID generado para el corte de evaluación: " + idGeneradoC);    
            }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return idGeneradoC;
}
}