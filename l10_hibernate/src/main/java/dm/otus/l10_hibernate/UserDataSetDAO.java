package dm.otus.l10_hibernate;

import dm.otus.l10_hibernate.entity.UserDataSet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

@SuppressWarnings("WeakerAccess")
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
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete PhoneDataSet");
        query.executeUpdate();
        query = session.createQuery("delete from AddressDataSet");
        query.executeUpdate();
        query = session.createQuery("delete from UserDataSet");
        query.executeUpdate();
        transaction.commit();
    }
}
