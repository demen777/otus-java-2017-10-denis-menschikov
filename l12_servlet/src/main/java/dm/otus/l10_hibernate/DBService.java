package dm.otus.l10_hibernate;

import dm.otus.l10_hibernate.entity.UserDataSet;

@SuppressWarnings("WeakerAccess")
public interface DBService {
    UserDataSet load(long id);
    void save(UserDataSet user);
    void shutdown();
    void clearAll();
}
