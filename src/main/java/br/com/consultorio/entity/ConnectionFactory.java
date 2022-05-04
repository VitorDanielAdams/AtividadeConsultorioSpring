package br.com.consultorio.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Atividadeconsultorio","postgres","123");
        }
        catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
