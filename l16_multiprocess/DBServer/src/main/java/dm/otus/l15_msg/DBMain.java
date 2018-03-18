package dm.otus.l15_msg;

import dm.otus.l15_msg.db.DBService;
import dm.otus.l15_msg.db.DBServiceHibernate;
import dm.otus.l15_msg.message_system.MessageSystemConnector;
import dm.otus.l15_msg.message_system.SocketMessageSystemConnector;

import java.io.IOException;

public class DBMain {
    private static final String MESSAGE_SYSTEM_HOST = "127.0.0.1";
    @SuppressWarnings("FieldCanBeLocal")
    private static final int MESSAGE_SYSTEM_PORT = 8888;

    public static void main(String[] args) throws IOException, InterruptedException {
        MessageSystemConnector messageSystemConnector = new SocketMessageSystemConnector(MESSAGE_SYSTEM_HOST,
                MESSAGE_SYSTEM_PORT);
        DBService dbService = new DBServiceHibernate(messageSystemConnector);
        DBWorkEmulator dbWorkEmulator = new DBWorkEmulator(dbService);
        //noinspection InfiniteLoopStatement
        while (true) {
            dbWorkEmulator.doSomeWork();
            Thread.sleep(1000);
        }
    }
}
