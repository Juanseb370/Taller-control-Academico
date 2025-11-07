package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

//---------------LISTAR


            public List<Curso> listarCursos() {
                List<Curso> lista = new ArrayList<>();
                String sql = "SELECT * FROM cursos";

                try (Connection con = ConexionBD.conectar();
                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Curso c = new Curso();
                        c.setCursoId(rs.getInt("curso_id"));
                        c.setNombreCurso(rs.getString("nombre_curso"));
                        c.setDescripcionCurso(rs.getString("descripcion_curso"));
                        c.setPeriodoAcademicoId(rs.getInt("periodo_academico_id"));
                        c.setDocenteId(rs.getInt("docente_id"));
                        lista.add(c);
                    }

                } catch (SQLException e) {
                    System.out.println("Error al listar cursos: " + e.getMessage());
                }
                return lista;
            }

//------------------ ELIMINAR

            public boolean eliminarCurso(int cursoId) {
                String sql = "DELETE FROM cursos WHERE curso_id = ?";
                try (Connection con = ConexionBD.conectar();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, cursoId);
                    int filas = ps.executeUpdate();
                    return filas > 0;
                } catch (Exception e) {
                    System.out.println(" Error al eliminar curso: " + e.getMessage());
                    return false;
                }
            }


}



//