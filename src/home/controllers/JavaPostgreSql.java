package home.controllers;

import java.sql.*;


public class JavaPostgreSql {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/students",
                "postgres",
                "spectre");

        return connection;

    }

}




