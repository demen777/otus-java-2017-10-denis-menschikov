package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.message_system.MessageSystemConnector;
import dm.otus.l15_msg.message_system.ServiceType;
import dm.otus.l15_msg.messages.SyncMsgCheckLogin;
import dm.otus.l15_msg.messages.SyncMsgCheckLoginAnswer;

import javax.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {
    private static final String LOGIN_ATTRIBUTE_NAME = "login";
    private final MessageSystemConnector messageSystemConnector;

    public AuthServiceImpl(MessageSystemConnector messageSystemConnector) {
        this.messageSystemConnector = messageSystemConnector;
        this.messageSystemConnector.setServiceObject(this);
        messageSystemConnector.registerService(new ServiceType(AuthService.class));
    }

    @Override
    public boolean isAuthSession(HttpSession session) {
        return session.getAttribute(LOGIN_ATTRIBUTE_NAME) != null;
    }

    @Override
    public boolean doAuth(HttpSession session, String login, String password) {
        SyncMsgCheckLogin msg = new SyncMsgCheckLogin(login, password);
        Boolean validLogin = ((SyncMsgCheckLoginAnswer) messageSystemConnector.sendMessageSync(msg)).isValidLogin();
        if (validLogin) {
            session.setAttribute(LOGIN_ATTRIBUTE_NAME, login);
            return true;
        }
        return false;
    }
}
