package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Asistencia;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsistenciaDAO {

    public boolean insertarAsistencia(Asistencia asistencia) {
        String sql = "INSERT INTO asistencias (estudiante_id, curso_id, fecha_clase, estado_asistencia, novedades) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, asistencia.getEstudianteId());
            stmt.setInt(2, asistencia.getCursoId());
            stmt.setDate(3, asistencia.getFechaClase());
            stmt.setString(4, asistencia.getEstadoAsistencia()); // ENUM: presente / ausente / tardanza
            stmt.setString(5, asistencia.getNovedades());

            int filas = stmt.executeUpdate();
            System.out.println(" Conexión exitosa a la base de datos");
            return filas > 0;

        } catch (SQLException e) {
            System.out.println(" Error al insertar asistencia: " + e.getMessage());
            return false;
        }
    }
}
