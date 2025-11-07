


package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Estudiante;

import java.sql.*;

public class EstudianteDAO {

    

    public int insertarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (identificacion, nombre, correo_institucional, correo_personal, telefono, es_vocero, comentarios, tipo_documento, genero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, estudiante.getIdentificacion());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreoInstitucional());
            ps.setString(4, estudiante.getCorreoPersonal());
            ps.setString(5, estudiante.getTelefono());
            ps.setBoolean(6, estudiante.isEsVocero());
            ps.setString(7, estudiante.getComentarios());
            ps.setString(8, estudiante.getTipoDocumento());
            ps.setString(9, estudiante.getGenero());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println(" Estudiante insertado correctamente.");
            }
            // Obtener el ID autogenerado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    System.out.println(" ID generado para el estudiante: " + idGenerado);
                }
            }
        } catch (SQLException e) {
            System.out.println(" Error al insertar estudiante: " + e.getMessage());
        }



        return idGenerado; // devuelve el ID autogenerado o -1 si falla
    }
}

