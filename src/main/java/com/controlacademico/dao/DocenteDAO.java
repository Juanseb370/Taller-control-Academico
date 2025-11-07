package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Docente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// public class DocenteDAO {

//     // 🔹 Método para insertar un docente en la base de datos
//     public boolean insertarDocente(Docente d) {
//         String sql = "INSERT INTO docentes (nombre_docente, identificacion, tipo_identificacion, genero, correo, titulo_estudios, idiomas, certificaciones) " +
//                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

//         try (Connection con = ConexionBD.conectar();
//              PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setString(1, d.getNombreDocente());
//             ps.setString(2, d.getIdentificacion());
//             ps.setString(3, d.getTipoIdentificacion());
//             ps.setString(4, d.getGenero());
//             ps.setString(5, d.getCorreo());
//             ps.setString(6, d.getTituloEstudios());
//             ps.setString(7, d.getIdiomas());
//             ps.setString(8, d.getCertificaciones());

//             int filas = ps.executeUpdate();
//             return filas > 0; // Retorna true si se insertó correctamente

//         } catch (SQLException ex) {
//             System.out.println(" Error al insertar docente: " + ex.getMessage());
//             return false;
//         }
//     }
// }


//-------------------------------------------------------------------------------------prueba 2


public class DocenteDAO {

    // 🔹 Método para insertar un docente y retornar su ID
    public int insertarDocente(Docente d) {
        String sql = "INSERT INTO docentes (nombre_docente, identificacion, tipo_identificacion, genero, correo, titulo_estudios, idiomas, certificaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getNombreDocente());
            ps.setString(2, d.getIdentificacion());
            ps.setString(3, d.getTipoIdentificacion());
            ps.setString(4, d.getGenero());
            ps.setString(5, d.getCorreo());
            ps.setString(6, d.getTituloEstudios());
            ps.setString(7, d.getIdiomas());
            ps.setString(8, d.getCertificaciones());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retorna el ID generado
                    }
                }
            }
            return -1; // Retorna -1 si no se insertó correctamente

        } catch (SQLException ex) {
            System.out.println(" Error al insertar docente: " + ex.getMessage());
            return -1;
        }
    }
}