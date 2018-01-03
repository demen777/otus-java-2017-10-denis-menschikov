package dm.otus.l10_hibernate;

import dm.otus.l10_hibernate.entity.AddressDataSet;
import dm.otus.l10_hibernate.entity.PhoneDataSet;
import dm.otus.l10_hibernate.entity.UserDataSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceHibernateTest {

    DBService dbService;

    @BeforeEach
    void setUp() throws Exception {
        dbService = new DBServiceHibernate();
    }

    @AfterEach
    void tearDown() {
        dbService.shutdown();
    }

    @Test
    void saveAndLoad() {
        dbService.clearAll();
        UserDataSet user1 = new UserDataSet("Anna", 36, new AddressDataSet("Amsterdam"),
                new ArrayList<>(Arrays.asList(new PhoneDataSet("222-222-22")))
        );
        dbService.save(user1);
        System.out.println(user1.getId());
        UserDataSet user2 = new UserDataSet("Ben", 30, new AddressDataSet("Berlin"),
                new ArrayList<>(Arrays.asList(new PhoneDataSet("333-222-22"),
                        new PhoneDataSet("444-222-22")))
        );
        dbService.save(user2);
        System.out.println(user2.getId());
    }
}