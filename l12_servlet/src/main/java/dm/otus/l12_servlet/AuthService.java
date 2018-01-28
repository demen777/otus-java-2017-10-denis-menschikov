package dm.otus.l12_servlet;

import javax.servlet.http.HttpSession;

@SuppressWarnings("WeakerAccess")
public interface AuthService {
    boolean isAuthSession(HttpSession session);
    boolean doAuth(HttpSession session, String login, String password);
}
