package dm.otus.l10_hibernate;

import dm.otus.l15_msg.entity.AddressDataSet;
import dm.otus.l15_msg.entity.PhoneDataSet;
import dm.otus.l15_msg.entity.UserDataSet;
import dm.otus.l15_msg.db.DBService;
import dm.otus.l15_msg.db.DBServiceHibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DBServiceHibernateTest {

    private DBService dbService;

    @BeforeEach
    void setUp() {
        dbService = new DBServiceHibernate();
    }

    @AfterEach
    void tearDown() {
        dbService.shutdown();
    }

    @Test
    void saveAndLoad() {
        dbService.clearAll();
        UserDataSet user1 = new UserDataSet("Anna", 36, new AddressDataSet("Amsterdam"));
        PhoneDataSet phone1 = new PhoneDataSet("222-222-22");
        user1.addPhone(phone1);
        dbService.save(user1);
        System.out.println(user1.getId());
        UserDataSet user2 = new UserDataSet("Ben", 30, new AddressDataSet("Berlin"));
        user2.addPhone(new PhoneDataSet("333-222-22"));
        user2.addPhone(new PhoneDataSet("444-222-22"));
        dbService.save(user2);
        System.out.println(user2.getId());
        UserDataSet loadedUser = dbService.load(user2.getId());
        System.out.println(loadedUser.getName());
    }
}