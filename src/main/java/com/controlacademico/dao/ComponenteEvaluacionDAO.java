package com.controlacademico.dao;
import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.ComponenteEvaluacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ComponenteEvaluacionDAO {




public int insertarComponenteEvaluacionYObtenerid(ComponenteEvaluacion componente) {
    String sql = "INSERT INTO componentes_evaluacion (corte_evaluacion_id, nombre_componente, porcentaje) VALUES (?, ?, ?)";

    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, componente.getCorteEvaluacionId());    
        ps.setString(2, componente.getNombreComponente());
        ps.setDouble(3, componente.getPorcentaje());

        int filas = ps.executeUpdate();

        if (filas > 0) {
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("✅ ID generado para el componente de evaluación: " + idGenerado);
                    return idGenerado;
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al insertar componente de evaluación: " + e.getMessage());
    }
    return -1;
}
}
