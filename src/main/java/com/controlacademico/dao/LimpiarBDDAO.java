package com.controlacademico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.controlacademico.config.ConexionBD;

public class LimpiarBDDAO {

    public static void eliminarTodosLosRegistros() {
        //  Orden correcto según dependencias (hijas → padres)
        String[] tablas = {
            "calificaciones",
            "asistencias",
            "componentes_evaluacion",
            "cortes_evaluacion",
            "docentes_cursos",
            "clases",
            "cursos",
            "periodos_academicos",
            "estudiantes",
            "docentes"
        };

        Connection con = null;

        try {
            con = ConexionBD.conectar();
            con.setAutoCommit(false); //  Iniciar transacción
            System.out.println("Conexión exitosa a la base de datos.");

            for (String tabla : tablas) {
                //  Eliminar registros
                String deleteSQL = "DELETE FROM " + tabla;
                try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                    int filas = ps.executeUpdate();
                    System.out.println(" Registros eliminados de la tabla " + tabla + ": " + filas);
                }

                //  Reiniciar ID AUTO_INCREMENT
                String resetSQL = "ALTER TABLE " + tabla + " AUTO_INCREMENT = 1";
                try (PreparedStatement psReset = con.prepareStatement(resetSQL)) {
                    psReset.executeUpdate();
                    System.out.println(" AUTO_INCREMENT reiniciado en la tabla " + tabla);
                }
            }

            con.commit(); //  Confirmar todo
            System.out.println(" Todos los registros fueron eliminados correctamente y los IDs reiniciados.");

        } catch (SQLException e) {
            System.out.println(" Error durante la limpieza de la base de datos: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback(); // Revertir con la misma conexión
                    System.out.println(" Cambios revertidos debido a un error.");
                }
            } catch (SQLException rollbackEx) {
                System.out.println(" Error al hacer rollback: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(" Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
