package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.message_system.MessageSystem;
import dm.otus.l15_msg.messages.SyncMsgCheckLogin;

import javax.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {
    private static final String LOGIN_ATTRIBUTE_NAME = "login";
    private final MessageSystem messageSystem;

    public AuthServiceImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public boolean isAuthSession(HttpSession session) {
        return session.getAttribute(LOGIN_ATTRIBUTE_NAME) != null;
    }

    @Override
    public boolean doAuth(HttpSession session, String login, String password) {
        SyncMsgCheckLogin msg = new SyncMsgCheckLogin(login, password);
        Boolean validLogin = (Boolean) messageSystem.sendMessageSync(this, msg);
        if (validLogin) {
            session.setAttribute(LOGIN_ATTRIBUTE_NAME, login);
            return true;
        }
        return false;
    }
}
