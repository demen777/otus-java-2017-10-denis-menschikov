package dm.otus.l15_msg.messages;

import dm.otus.l15_msg.api.DBServiceAPI;
import dm.otus.l15_msg.message_system.ServiceType;
import dm.otus.l15_msg.message_system.SyncMessage;

@SuppressWarnings("WeakerAccess")
public class SyncMsgCheckLogin extends SyncMessage {
    private final ServiceType serviceType = new ServiceType(DBServiceAPI.class);
    private final String login;
    private final String password;

    public SyncMsgCheckLogin(String login, String password) {
        super(SyncMsgCheckLogin.class);
        this.login = login;
        this.password = password;
    }

    public void exec(Object receiver) {
        DBServiceAPI dbService = (DBServiceAPI)receiver;
        boolean isValidLogin = dbService.checkLogin(login, password);
        sendReply(new SyncMsgCheckLoginAnswer(isValidLogin));
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }
}
