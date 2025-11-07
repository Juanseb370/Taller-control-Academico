package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.PeriodoAcademico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodoAcademicoDAO {

//     //  Método para insertar un periodo académico
//     public boolean insertarPeriodoAcademico(PeriodoAcademico p) {
//         String sql = "INSERT INTO periodos_academicos (nombre_periodo, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";

//         try (Connection con = ConexionBD.conectar();
//              PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setString(1, p.getNombrePeriodo());
//             ps.setDate(2, p.getFechaInicio());
//             ps.setDate(3, p.getFechaFin());

//             int filas = ps.executeUpdate();
//             return filas > 0; // true si se insertó correctamente

//         } catch (SQLException ex) {
//             System.out.println(" Error al insertar periodo académico: " + ex.getMessage());
//             return false;
//         }
//     }
// }




//-------------------------------------------------------------------------------------

    //  Método para insertar un periodo académico y retornar su ID
    public int insertarPeriodoAcademico(PeriodoAcademico p) {
        String sql = "INSERT INTO periodos_academicos (nombre_periodo, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombrePeriodo());
            ps.setDate(2, p.getFechaInicio());
            ps.setDate(3, p.getFechaFin());

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
            System.out.println(" Error al insertar periodo académico: " + ex.getMessage());
            return -1;
        }
    }
}








