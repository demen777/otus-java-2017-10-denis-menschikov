package dm.otus.sql;

import dm.otus.sql.entity.UserDataSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class DBServiceJDBCTest {

    private DBService dbService;

    @BeforeAll
    static void beforeAll() throws SQLException {
        ConnectionFactory.init("jdbc:postgresql://dmserver:5432/l9sql", "l9sql_user", "qaz123");
        UserDAO userDAO = new UserDAO();
        userDAO.createDBObjects();
    }

    @BeforeEach
    void setUp() {
        dbService = new DBServiceJDBC();
    }

    @AfterEach
    void tearDown() {
        dbService.shutdown();
    }

    @Test
    void saveAndLoad() {
        dbService.clearAll();
        UserDataSet user1 = new UserDataSet("Anna", 36);
        dbService.save(user1);
        System.out.println(user1.getId());
        UserDataSet user2 = new UserDataSet("Ben", 30);
        dbService.save(user2);
        System.out.println(user2.getId());
        UserDataSet loadedUser = dbService.load(user2.getId());
        System.out.println(loadedUser.getName());
    }

}