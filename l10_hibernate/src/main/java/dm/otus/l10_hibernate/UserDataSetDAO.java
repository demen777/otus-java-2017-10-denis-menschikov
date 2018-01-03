package dm.otus.l10_hibernate;

import dm.otus.l10_hibernate.entity.UserDataSet;
import org.hibernate.Session;

public class UserDataSetDAO {
    private final Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public UserDataSet load(long id) {
        return session.load(UserDataSet.class, id);
    }

    public void save(UserDataSet user) {
        session.save(user);
    }

    public void clearAll() {
        
    }
}
