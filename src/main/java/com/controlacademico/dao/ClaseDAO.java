package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClaseDAO {

    public boolean insertarClase(Clases clase) {
        String sql = "INSERT INTO clases (curso_id, numero_clase, fecha_clase, tema_clase, descripcion_clase, comentarios_clase) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, clase.getCursoId());
            stmt.setInt(2, clase.getNumeroClase());
            stmt.setDate(3, clase.getFechaClase());
            stmt.setString(4, clase.getTemaClase());
            stmt.setString(5, clase.getDescripcionClase());
            stmt.setString(6, clase.getComentariosClase());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println(" Error al insertar clase: " + e.getMessage());
            return false;
        }
    }
}
