// package com.controlacademico.dao;

// import com.controlacademico.config.ConexionBD;
// import com.controlacademico.modelo.Calificacion;


// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// public class CalificacionDAO {

//     public boolean insertarCalificacion(Calificacion calificacion) {
//         String sql = "INSERT INTO calificaciones (estudiante_id, componente_evaluacion_id, nota, comentarios_calificacion) VALUES (?, ?, ?, ?)";


//       try (Connection conn = ConexionBD.conectar();
//               PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, calificacion.getEstudianteId());
//             ps.setInt(2, calificacion.getComponente_evaluacionid());
//             ps.setDouble(3, calificacion.getNota());
//             ps.setString(4, calificacion.getComentariosCalificacion());



                

//             int filas = ps.executeUpdate();
//             return filas > 0;

//         } catch (SQLException e) {
//             System.err.println("Error al insertar calificación: " + e.getMessage());
//             return false;
//         }
//     }
// }








package com.controlacademico.dao;

import com.controlacademico.config.ConexionBD;
import com.controlacademico.modelo.Calificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalificacionDAO {

    public boolean insertarCalificacion(Calificacion calificacion) {
        String sql = "INSERT INTO calificaciones (estudiante_id, componente_evaluacion_id, nota, comentarios_calificacion) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
        
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, calificacion.getEstudianteId());
            ps.setInt(2, calificacion.getComponente_evaluacionId());
            ps.setDouble(3, calificacion.getNota());
            ps.setString(4, calificacion.getComentariosCalificacion()); 
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println(" Error al insertar calificación: " + e.getMessage());   
            return false;
        }
    }
    }

