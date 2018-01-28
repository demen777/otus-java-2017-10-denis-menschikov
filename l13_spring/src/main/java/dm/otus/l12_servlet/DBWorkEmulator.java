package dm.otus.l12_servlet;

import dm.otus.l10_hibernate.DBService;
import dm.otus.l10_hibernate.entity.AddressDataSet;
import dm.otus.l10_hibernate.entity.PhoneDataSet;
import dm.otus.l10_hibernate.entity.UserDataSet;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class DBWorkEmulator {
    private final DBService dbService;
    private final ArrayList<Long> userIdList;
    private final Random randomGenerator;

    public DBWorkEmulator(DBService dbService) {
        this.dbService = dbService;
        this.userIdList = new ArrayList<>();
        this.randomGenerator = new Random();
        fillDB();
    }

    private void fillDB() {
        dbService.clearAll();
        UserDataSet user1 = new UserDataSet("Anna", 36, new AddressDataSet("Amsterdam"));
        user1.addPhone(new PhoneDataSet("222-222-22"));
        dbService.save(user1);
        userIdList.add(user1.getId());
        UserDataSet user2 = new UserDataSet("Ben", 30, new AddressDataSet("Berlin"));
        user2.addPhone(new PhoneDataSet("333-222-22"));
        user2.addPhone(new PhoneDataSet("444-222-22"));
        dbService.save(user2);
        userIdList.add(user2.getId());
        UserDataSet user3 = new UserDataSet("Carl", 32, new AddressDataSet("Copenhagen"));
        dbService.save(user3);
        userIdList.add(user3.getId());
        UserDataSet user4 = new UserDataSet("Den", 36, new AddressDataSet("Dresden"));
        user4.addPhone(new PhoneDataSet("555-222-22"));
        dbService.save(user4);
        userIdList.add(user4.getId());
    }

    public void doSomeWork() {
        int index = randomGenerator.nextInt(userIdList.size());
        dbService.load(userIdList.get(index));
    }
}
