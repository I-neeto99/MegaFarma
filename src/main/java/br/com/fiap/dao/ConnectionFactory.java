package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;

    // Dados da conexão com o Oracle da FIAP.
    // Se as variáveis de ambiente DB_URL / DB_USER / DB_PASSWORD existirem,
    // elas têm prioridade sobre os valores abaixo.
    private static final String URL_PADRAO   = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER_PADRAO  = "RM572912";
    private static final String PASS_PADRAO  = "fiap25";

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

            // System.getenv recebe o NOME da variável de ambiente, não o valor.
            String url  = valorOuPadrao("DB_URL",      URL_PADRAO);
            String user = valorOuPadrao("DB_USER",     USER_PADRAO);
            String pass = valorOuPadrao("DB_PASSWORD", PASS_PADRAO);

            connection = DriverManager.getConnection(url, user, pass);
            return connection;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage(), e);
        }
    }

    /** Lê a variável de ambiente; se ela não existir, usa o valor padrão. */
    private static String valorOuPadrao(String nomeDaVariavel, String padrao) {
        String valor = System.getenv(nomeDaVariavel);
        return (valor != null && !valor.isBlank()) ? valor : padrao;
    }
}