package dm.otus.l15_msg.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractHttpServlet extends HttpServlet {
    @SuppressWarnings("unused")
    @Autowired
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

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
