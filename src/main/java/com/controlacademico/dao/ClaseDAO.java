package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    

    //------------------------- LISTAR

        public List<Clases> listarClases() {
        List<Clases> lista = new ArrayList<>();
        String sql = "SELECT * FROM clases";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Clases c = new Clases();
                c.setClaseId(rs.getInt("clase_id"));
                c.setCursoId(rs.getInt("curso_id"));
                c.setNumeroClase(rs.getInt("numero_clase"));
                c.setFechaClase(rs.getDate("fecha_clase"));
                c.setTemaClase(rs.getString("tema_clase"));
                c.setDescripcionClase(rs.getString("descripcion_clase"));
                c.setComentariosClase(rs.getString("comentarios_clase"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clases: " + e.getMessage());
        }
        return lista;
    }



    //-----------------ELIMINAR

            public boolean eliminarClase(int claseId) {
            String sql = "DELETE FROM clases WHERE clase_id = ?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, claseId);
                int filas = ps.executeUpdate();
                return filas > 0;
            } catch (Exception e) {
                System.out.println(" Error al eliminar clase: " + e.getMessage());
                return false;
            }
        }


}
