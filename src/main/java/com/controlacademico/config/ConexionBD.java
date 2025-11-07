package com.controlacademico.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/control-academico";
    private static final String USER = "root";
    private static final String PASSWORD = "Pancha2025"; // <-- cámbiala

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrar(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    
}
