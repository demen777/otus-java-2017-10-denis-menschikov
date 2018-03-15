package dm.otus.l15_msg.db;

import dm.otus.l15_msg.entity.UserDataSet;
import dm.otus.l15_msg.message_system.MessageSystem;
import dm.otus.l15_msg.message_system.ServiceType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBServiceHibernate implements DBService, CacheInfo {
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageSystem messageSystem;
    private final SessionFactory sessionFactory;
    private Cache<Long, UserDataSet> cache;

    public DBServiceHibernate(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                // configures settings from hibernate.cfg.xml
                .configure("hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
        createCache();
        messageSystem.addReceiver(new ServiceType(CacheInfo.class), this);
        messageSystem.addReceiver(new ServiceType(DBService.class), this);
    }

    private void createCache() {
        cache = new CacheImpl<>(2, 0, 0);
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

    @Override
    public Boolean checkLogin(String login, String password) {
        try (Session session=sessionFactory.openSession()){
            LoginDAO loginDAO = new LoginDAO(session);
            return loginDAO.checkLogin(login, password);
        }
    }

    @Override
    public long getHitCount() {
        return cache.getHitCount();
    }

    @Override
    public long getMissCount() {
        return cache.getMissCount();
    }
}
