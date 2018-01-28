package dm.otus.l12_servlet;

import javax.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {
    private static final String LOGIN_ATTRIBUTE_NAME = "login";
    private static final String VALID_LOGIN = "admin";
    private static final String VALID_PASSWORD = "1234";

    @Override
    public boolean isAuthSession(HttpSession session) {
        return session.getAttribute(LOGIN_ATTRIBUTE_NAME) != null;
    }

    @Override
    public boolean doAuth(HttpSession session, String login, String password) {
        if (login.equals(VALID_LOGIN) && password.equals(VALID_PASSWORD)) {
            session.setAttribute(LOGIN_ATTRIBUTE_NAME, login);
            return true;
        }
        return false;
    }
}
