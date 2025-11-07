package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CursoDAO {




public int insertarCursoYObtenerId(Curso curso) {
    String sql = "INSERT INTO cursos (nombre_curso, descripcion_curso, periodo_academico_id, docente_id) VALUES (?, ?, ?, ?)";
    int idGenerado = -1;

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, curso.getNombreCurso());
        ps.setString(2, curso.getDescripcionCurso());
        ps.setInt(3, curso.getPeriodoAcademicoId());
        ps.setInt(4, curso.getDocenteId());

        ps.executeUpdate();
// Obtener el ID generado
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            System.out.println("✅ ID generado para el curso: " + idGenerado);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return idGenerado;
}
}









//     // Método para insertar un curso en la base de datos
//     public boolean insertarCurso(Curso c) {
//         String sql = "INSERT INTO cursos (nombre_curso, periodo_academico_id, docente_id, descripcion_curso) " +
//                      "VALUES (?, ?, ?, ?)";

//         try (Connection con = ConexionBD.conectar();
//              PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setString(1, c.getNombreCurso());
//             ps.setInt(2, c.getPeriodoAcademicoId());
//             ps.setInt(3, c.getDocenteId());
//             ps.setString(4, c.getDescripcionCurso());

//             int filas = ps.executeUpdate();
//             return filas > 0; // Retorna true si se insertó correctamente

//         } catch (SQLException ex) {
//             System.out.println(" Error al insertar curso: " + ex.getMessage());
//             return false;
//         }
//     }
// }
