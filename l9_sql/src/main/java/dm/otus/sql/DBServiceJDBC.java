package dm.otus.sql;

import dm.otus.sql.entity.UserDataSet;

import java.sql.SQLException;

public class DBServiceJDBC implements DBService {
    private final UserDAO userDAO;

    public DBServiceJDBC() {
        userDAO = new UserDAO();
    }

    @Override
    public UserDataSet load(long id) {
        try {
            return userDAO.load(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(UserDataSet user) {
        try {
            userDAO.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void clearAll() {
        try {
            userDAO.clearAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
