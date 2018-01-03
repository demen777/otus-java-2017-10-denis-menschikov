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
    }

    public UserDataSet load(long id) {
        try (Session session=sessionFactory.openSession()){
            UserDAO userDAO = new UserDAO(session);
            return userDAO.load(id);
        }
    }

    public void save(UserDataSet user) {
        try (Session session=sessionFactory.openSession()){
            UserDAO userDAO = new UserDAO(session);
            userDAO.save(user);
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
        }
    }
}
