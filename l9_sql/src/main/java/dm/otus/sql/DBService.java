package dm.otus.sql;


import dm.otus.sql.entity.UserDataSet;

@SuppressWarnings("WeakerAccess")
public interface DBService {
    UserDataSet load(long id);
    void save(UserDataSet user);
    void shutdown();
    void clearAll();
}
