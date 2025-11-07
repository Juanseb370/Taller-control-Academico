package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Calificacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionDAO {

    public int insertarCalificacionYObtenerId(Calificacion calificacion) {
        String sql = "INSERT INTO calificaciones (estudiante_id, componente_evaluacion_id, nota, comentarios_calificacion) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, calificacion.getEstudianteId());
            ps.setInt(2, calificacion.getComponenteEvaluacionId());
            ps.setDouble(3, calificacion.getNota());
            ps.setString(4, calificacion.getComentariosCalificacion());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        System.out.println(" Calificación insertada con ID: " + idGenerado);
                        return idGenerado;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al insertar calificación: " + e.getMessage());
        }

        return -1;
    }



    //---------------LISTAR

        public List<Calificacion> listarCalificaciones() {
        List<Calificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM calificaciones";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Calificacion c = new Calificacion();
                c.setCalificacionId(rs.getInt("calificacion_id"));
                c.setEstudianteId(rs.getInt("estudiante_id"));
                c.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
                c.setNota(rs.getDouble("nota"));
                c.setComentariosCalificacion(rs.getString("comentarios_calificacion"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar calificaciones: " + e.getMessage());
        }
        return lista;
    }

}
