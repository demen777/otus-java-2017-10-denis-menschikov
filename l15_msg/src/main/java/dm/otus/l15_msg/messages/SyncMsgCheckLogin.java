package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.db.DBService;
import dm.otus.l15_msg.message_system.ServiceType;
import dm.otus.l15_msg.message_system.SyncMessage;

public class SyncMsgCheckLogin extends SyncMessage {
    private final ServiceType serviceType = new ServiceType(DBService.class);
    private final String login;
    private final String password;

    public SyncMsgCheckLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Object call(Object receiver) {
        DBService dbService = (DBService)receiver;
        return dbService.checkLogin(login, password);
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }
}
