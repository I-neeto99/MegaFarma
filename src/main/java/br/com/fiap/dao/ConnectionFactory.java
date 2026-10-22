package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            String url  = System.getenv("jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL");
            String user = System.getenv("RM572912");
            String pass = System.getenv("fiap25");

           // if (url == null || user == null || pass == null) {
             //   throw new IllegalStateException(
               //         "Variáveis de ambiente não configuradas: DB_URL, DB_USER, DB_PASSWORD");
            //}

            connection = DriverManager.getConnection(url, user, pass);
            return connection;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage(), e);
        }
    }
}