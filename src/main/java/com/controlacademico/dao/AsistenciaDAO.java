package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Asistencia;
import java.sql.*;

public class AsistenciaDAO {

    public int insertarAsistenciaYObtenerId(Asistencia asistencia) {
        String sql = "INSERT INTO asistencias (estudiante_id, curso_id, fecha_clase, estado_asistencia, novedades) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asistencia.getEstudianteId());
            ps.setInt(2, asistencia.getCursoId());
            ps.setDate(3, asistencia.getFechaClase());
            ps.setString(4, asistencia.getEstadoAsistencia());
            ps.setString(5, asistencia.getNovedades());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        System.out.println(" Asistencia insertada con ID: " + idGenerado);
                        return idGenerado;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al insertar asistencia: " + e.getMessage());
        }

        return -1;
    }
}
