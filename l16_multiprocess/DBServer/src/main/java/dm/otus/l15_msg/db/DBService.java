package dm.otus.l15_msg.db;

import dm.otus.l15_msg.api.DBServiceAPI;
import dm.otus.l15_msg.entity.UserDataSet;

@SuppressWarnings("WeakerAccess")
public interface DBService extends DBServiceAPI {
    @SuppressWarnings("UnusedReturnValue")
    UserDataSet load(long id);
    void save(UserDataSet user);
    void shutdown();
    void clearAll();
}
