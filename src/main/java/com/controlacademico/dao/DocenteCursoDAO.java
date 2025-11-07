package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.DocenteCurso;


import java.sql.Connection;
import java.sql.PreparedStatement;

public class DocenteCursoDAO {

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
}

