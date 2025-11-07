


package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    //----------------- INSERTAR ESTUDIANTE Y OBTENER ID AUTOGENERADO -----------------

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




//------------------ LISTAR--------------------------

        public List<Estudiante> listarEstudiantes() {
            List<Estudiante> lista = new ArrayList<>();
            String sql = "SELECT * FROM estudiantes";

            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Estudiante e = new Estudiante();
                    e.setEstudianteId(rs.getInt("estudiante_id"));
                    e.setIdentificacion(rs.getString("identificacion"));
                    e.setNombre(rs.getString("nombre"));
                    e.setCorreoInstitucional(rs.getString("correo_institucional"));
                    e.setCorreoPersonal(rs.getString("correo_personal"));
                    e.setTelefono(rs.getString("telefono"));
                    e.setEsVocero(rs.getBoolean("es_vocero"));
                    e.setComentarios(rs.getString("comentarios"));
                    e.setTipoDocumento(rs.getString("tipo_documento"));
                    e.setGenero(rs.getString("genero"));
                    lista.add(e);
                }

            } catch (SQLException e) {
                System.out.println("Error al listar estudiantes: " + e.getMessage());
            }
            return lista;
        }


//----------------ELIMINAR


            public boolean eliminarEstudiante(int estudianteId) {
                String sql = "DELETE FROM estudiantes WHERE estudiante_id = ?";
                try (Connection con = ConexionBD.conectar();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, estudianteId);
                    int filas = ps.executeUpdate();
                    return filas > 0;
                } catch (Exception e) {
                    System.out.println(" Error al eliminar estudiante: " + e.getMessage());
                    return false;
                }
            }
































            

}


//