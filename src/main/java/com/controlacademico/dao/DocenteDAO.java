package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Docente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class DocenteDAO {

    //  Método para insertar un docente y retornar su ID
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




    //-------LISTAR 


        public List<Docente> listarDocentes() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Docente d = new Docente();
                d.setDocenteId(rs.getInt("docente_id"));
                d.setNombreDocente(rs.getString("nombre_docente"));
                d.setIdentificacion(rs.getString("identificacion"));
                d.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                d.setGenero(rs.getString("genero"));
                d.setCorreo(rs.getString("correo"));
                d.setTituloEstudios(rs.getString("titulo_estudios"));
                d.setIdiomas(rs.getString("idiomas"));
                d.setCertificaciones(rs.getString("certificaciones"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar docentes: " + e.getMessage());
        }
        return lista;
    }



    //---------------ELIMINAR

            public boolean eliminarDocente(int docenteId) {
            String sql = "DELETE FROM docentes WHERE docente_id = ?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, docenteId);
                int filas = ps.executeUpdate();
                return filas > 0;
            } catch (Exception e) {
                System.out.println(" Error al eliminar docente: " + e.getMessage());
                return false;
            }
        }



        //--------------ACTUALIZAR

            public boolean actualizarDocente(Docente docente) {
        String sql = "UPDATE docentes SET nombre_docente=?, identificacion=?, tipo_identificacion=?, genero=?, correo=?, titulo_estudios=?, idiomas=?, certificaciones=? WHERE docente_id=?";
        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, docente.getNombreDocente());
            ps.setString(2, docente.getIdentificacion());
            ps.setString(3, docente.getTipoIdentificacion());
            ps.setString(4, docente.getGenero());
            ps.setString(5, docente.getCorreo());
            ps.setString(6, docente.getTituloEstudios());
            ps.setString(7, docente.getIdiomas());
            ps.setString(8, docente.getCertificaciones());
            ps.setInt(9, docente.getDocenteId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar docente: " + e.getMessage());
            return false;
        }
    }




}