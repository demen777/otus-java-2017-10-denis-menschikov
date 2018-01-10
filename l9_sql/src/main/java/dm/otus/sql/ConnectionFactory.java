package dm.otus.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class ConnectionFactory {
    private static String url;
    private static String username;
    private static String password;

    public static void init(String url, String username, String password) {
        ConnectionFactory.url = url;
        ConnectionFactory.username = username;
        ConnectionFactory.password = password;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(boolean isAutoCommit) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(isAutoCommit);
        return connection;
    }
}
