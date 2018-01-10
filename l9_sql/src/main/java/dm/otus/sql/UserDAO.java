package dm.otus.sql;

import dm.otus.sql.entity.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class UserDAO {

    public UserDataSet load(long id) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection(true)){
            DBExecutor dbExecutor = new DBExecutor(connection);
            return dbExecutor.load(id, UserDataSet.class);
        }
    }

    public void save(UserDataSet user) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection(true)){
            DBExecutor dbExecutor = new DBExecutor(connection);
            dbExecutor.save(user);
        }
    }

    public void createDBObjects() throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection(true)){
            DBExecutor dbExecutor = new DBExecutor(connection);
            dbExecutor.createDBObjects(UserDataSet.class);
        }
    }

    public void clearAll() throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection(true)){
            DBExecutor dbExecutor = new DBExecutor(connection);
            dbExecutor.clearAll(UserDataSet.class);
        }
    }
}
