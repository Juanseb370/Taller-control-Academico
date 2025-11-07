package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    public int insertarAsistenciaYObtenerId(Asistencia asistencia) {
        String sql = "INSERT INTO asistencias (estudiante_id, curso_id, fecha_clase, estado_asistencia, novedades) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, asistencia.getEstudianteId());
            ps.setInt(2, asistencia.getCursoId());
            ps.setDate(3, asistencia.getFechaClase());
            ps.setString(4, asistencia.getEstadoAsistencia());
            ps.setString(5, asistencia.getNovedades());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        System.out.println(" Asistencia insertada con ID: " + idGenerado);
                        return idGenerado;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al insertar asistencia: " + e.getMessage());
        }

        return -1;
    }



    //--------- LISTAR

        public List<Asistencia> listarAsistencias() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencias";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia a = new Asistencia();
                a.setAsistenciaId(rs.getInt("asistencia_id"));
                a.setEstudianteId(rs.getInt("estudiante_id"));
                a.setCursoId(rs.getInt("curso_id"));
                a.setFechaClase(rs.getDate("fecha_clase"));
                a.setEstadoAsistencia(rs.getString("estado_asistencia"));
                a.setNovedades(rs.getString("novedades"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar asistencias: " + e.getMessage());
        }
        return lista;
    }


    //--------------------ELIMINAR

            public boolean eliminarAsistencia(int asistenciaId) {
            String sql = "DELETE FROM asistencias WHERE asistencia_id = ?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, asistenciaId);
                int filas = ps.executeUpdate();
                return filas > 0;
            } catch (Exception e) {
                System.out.println(" Error al eliminar asistencia: " + e.getMessage());
                return false;
            }
        }

//-------------------ACTUALIZAR

            public boolean actualizarAsistencia(Asistencia asistencia) {
                String sql = "UPDATE asistencias SET estudiante_id=?, curso_id=?, fecha_clase=?, estado_asistencia=?, novedades=? WHERE asistencia_id=?";
                try (Connection con = ConexionBD.conectar();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, asistencia.getEstudianteId());
                    ps.setInt(2, asistencia.getCursoId());
                    ps.setDate(3, asistencia.getFechaClase());
                    ps.setString(4, asistencia.getEstadoAsistencia());
                    ps.setString(5, asistencia.getNovedades());
                    ps.setInt(6, asistencia.getAsistenciaId());
                    return ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    System.out.println("Error al actualizar asistencia: " + e.getMessage());
                    return false;
                }
            }


}
