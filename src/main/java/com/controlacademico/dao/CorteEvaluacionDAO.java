package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.CorteEvaluacion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class CorteEvaluacionDAO {

    public int insertarCorteEvaluacionYObtenerId(CorteEvaluacion corte) {
        String sql = "INSERT INTO cortes_evaluacion (curso_id, nombre_corte, porcentaje) VALUES (?, ?, ?)";
        int idGeneradoC = -1;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, corte.getCursoId());
            ps.setString(2, corte.getNombreCorte());
            ps.setDouble(3, corte.getPorcentaje());

                ps.executeUpdate();
            
            
    // Obtener el ID generado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGeneradoC = rs.getInt(1);
                System.out.println(" ID generado para el corte de evaluación: " + idGeneradoC);    
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGeneradoC;
    }



//---------------LISTAR

    public List<CorteEvaluacion> listarCortesEvaluacion() {
        List<CorteEvaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM cortes_evaluacion";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CorteEvaluacion c = new CorteEvaluacion();
                c.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
                c.setCursoId(rs.getInt("curso_id"));
                c.setPeriodoAcademicoId(rs.getInt("periodo_academico_id"));
                c.setNombreCorte(rs.getString("nombre_corte"));
                c.setPorcentaje(rs.getDouble("porcentaje"));
                c.setComentariosCorte(rs.getString("comentarios_corte"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar cortes de evaluación: " + e.getMessage());
        }
        return lista;
    }



    //------------------ ELIMINAR

        public boolean eliminarCorteEvaluacion(int corteId) {
        String sql = "DELETE FROM cortes_evaluacion WHERE corte_evaluacion_id = ?";
        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, corteId);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception e) {
            System.out.println(" Error al eliminar corte de evaluación: " + e.getMessage());
            return false;
        }
    }

    //--------------------------------ACTUALIZAR

            public boolean actualizarCorteEvaluacion(CorteEvaluacion corte) {
            String sql = "UPDATE cortes_evaluacion SET curso_id=?, periodo_academico_id=?, nombre_corte=?, porcentaje=?, comentarios_corte=? WHERE corte_evaluacion_id=?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, corte.getCursoId());
                ps.setInt(2, corte.getPeriodoAcademicoId());
                ps.setString(3, corte.getNombreCorte());
                ps.setDouble(4, corte.getPorcentaje());
                ps.setString(5, corte.getComentariosCorte());
                ps.setInt(6, corte.getCorteEvaluacionId());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Error al actualizar corte de evaluación: " + e.getMessage());
                return false;
            }
        }







}