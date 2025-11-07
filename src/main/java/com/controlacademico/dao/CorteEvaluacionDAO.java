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
                System.out.println("✅ ID generado para el corte de evaluación: " + idGeneradoC);    
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


}