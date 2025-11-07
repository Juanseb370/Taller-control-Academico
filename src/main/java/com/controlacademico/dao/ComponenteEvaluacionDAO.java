package com.controlacademico.dao;
import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.ComponenteEvaluacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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




//---------------LISTAR

        public List<ComponenteEvaluacion> listarComponentesEvaluacion() {
        List<ComponenteEvaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM componentes_evaluacion";

        try (Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ComponenteEvaluacion ce = new ComponenteEvaluacion();
                ce.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
                ce.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
                ce.setNombreComponente(rs.getString("nombre_componente"));
                ce.setPorcentaje(rs.getDouble("porcentaje"));
                lista.add(ce);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar componentes de evaluación: " + e.getMessage());
        }
        return lista;
    }


    //---------------ELIMINAR

            public boolean eliminarComponenteEvaluacion(int componenteId) {
            String sql = "DELETE FROM componentes_evaluacion WHERE componente_evaluacion_id = ?";
            try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, componenteId);
                int filas = ps.executeUpdate();
                return filas > 0;
            } catch (Exception e) {
                System.out.println(" Error al eliminar componente de evaluación: " + e.getMessage());
                return false;
            }
        }


}
