package dm.otus.l12_servlet;

import dm.otus.l10_hibernate.DBServiceHibernate;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

class Main {
    private final static int PORT = 8090;

    public static void main(String[] args) throws Exception {
        DBServiceHibernate dbService = new DBServiceHibernate();
        DBWorkEmulator dbWorkEmulator = new DBWorkEmulator(dbService);
        AuthService authService = new AuthServiceImpl();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new MainServlet(authService)), MainServlet.PATH);
        context.addServlet(new ServletHolder(new LoginServlet(authService)), LoginServlet.PATH);
        context.addServlet(new ServletHolder(new CacheStateServlet(authService, dbService, dbWorkEmulator)),
                CacheStateServlet.PATH);

        Server server = new Server(PORT);
        server.setHandler(context);

        server.start();
        server.join();
        dbService.shutdown();
    }
}
