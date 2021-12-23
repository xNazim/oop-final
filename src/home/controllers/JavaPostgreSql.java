package home.controllers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSql {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/students",
                "postgres",
                "spectre");

        return connection;

    }

}




