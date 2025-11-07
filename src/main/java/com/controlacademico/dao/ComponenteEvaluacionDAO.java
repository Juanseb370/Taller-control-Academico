package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.ComponenteEvaluacion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComponenteEvaluacionDAO {

    public boolean insertarComponenteEvaluacion(ComponenteEvaluacion componente) {
        String sql = "INSERT INTO componentes_evaluacion (corte_evaluacion_id, nombre_componente, porcentaje) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, componente.getCorteEvaluacionId());
            ps.setString(2, componente.getNombreComponente());
            ps.setDouble(3, componente.getPorcentaje());


            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar componente de evaluación: " + e.getMessage());
            return false;
        }
    }

    
}
