package dm.otus.l10_hibernate;

import dm.otus.l10_hibernate.entity.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class DBServiceHibernate implements DBService {
    private final SessionFactory sessionFactory;
    private Cache<Long, UserDataSet> cache;

    public DBServiceHibernate() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                // configures settings from hibernate.cfg.xml
                .configure(new File("src/main/resources/hibernate.cfg.xml"))
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
        createCache();
    }

    private void createCache() {
        cache = new CacheImpl<>(1000, 5*60*1000, 0);
    }

    public UserDataSet load(long id) {
        UserDataSet res = cache.get(id);
        if (res != null) {
            return res;
        }
        try (Session session=sessionFactory.openSession()){
            UserDAO userDAO = new UserDAO(session);
            return userDAO.load(id);
        }
    }

    public void save(UserDataSet user) {
        try (Session session=sessionFactory.openSession()){
            UserDAO userDAO = new UserDAO(session);
            userDAO.save(user);
            cache.put(user.getId(), user);
        }
    }

    public void shutdown() {
        sessionFactory.close();
    }

    @Override
    public void clearAll() {
        try (Session session=sessionFactory.openSession()){
            UserDAO userDAO = new UserDAO(session);
            userDAO.clearAll();
            cache.dispose();
            createCache();
        }
    }
}
