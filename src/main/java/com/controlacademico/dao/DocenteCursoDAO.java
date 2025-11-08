package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.DocenteCurso;


import java.sql.Connection;
import java.sql.PreparedStatement;

public class DocenteCursoDAO {

    //----------------  INSERTAR RELACIÓN DOCENTE-CURSO
    public boolean insertarDocenteCurso(DocenteCurso relacion) {
        String sql = "INSERT INTO docentes_cursos (docente_id, curso_id) VALUES (?, ?)";
        boolean insertado = false;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, relacion.getDocenteId());
            ps.setInt(2, relacion.getCursoId());    

                

            insertado = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertado;
    }




    //------------------ ELIMINAR RELACIÓN DOCENTE-CURSO


            public boolean eliminarDocenteCurso(int docenteId, int cursoId) {
            String sql = "DELETE FROM docentes_cursos WHERE docente_id = ? AND curso_id = ?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, docenteId);
                ps.setInt(2, cursoId);
                int filas = ps.executeUpdate();
                return filas > 0;
            } catch (Exception e) {
                System.out.println(" Error al eliminar relación docente-curso: " + e.getMessage());
                return false;
            }
        }



}

