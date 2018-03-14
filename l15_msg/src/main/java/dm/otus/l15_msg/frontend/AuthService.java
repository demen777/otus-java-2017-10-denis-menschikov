package dm.otus.l15_msg.frontend;

import javax.servlet.http.HttpSession;

@SuppressWarnings("WeakerAccess")
public interface AuthService {
    boolean isAuthSession(HttpSession session);
    boolean doAuth(HttpSession session, String login, String password);
}
