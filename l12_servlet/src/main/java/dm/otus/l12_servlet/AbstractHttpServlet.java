package dm.otus.l12_servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractHttpServlet extends HttpServlet {
    private final AuthService authService;

    public AbstractHttpServlet(AuthService authService) {
        this.authService = authService;
    }

    @SuppressWarnings("unused")
    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!authService.isAuthSession(request.getSession())) {
            response.sendRedirect(LoginServlet.PATH);
            return;
        }
        doRequest(request, response);
    }
}
