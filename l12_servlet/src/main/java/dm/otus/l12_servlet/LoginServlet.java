package dm.otus.l12_servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    public static final String PATH = "/login";
    private static final String TEMPLATE_FILENAME = "login.html";
    private final AuthService authService;

    public LoginServlet(AuthService authService) {
        this.authService = authService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        final String requestLogin = request.getParameter("login");
        final String requestPassword = request.getParameter("password");
        String errorMsg = "";

        if (requestLogin != null && requestPassword != null) {
            if (authService.doAuth(request.getSession(), requestLogin, requestPassword)) {
                response.sendRedirect(CacheStateServlet.PATH);
                return;
            }
            else {
                errorMsg = "Wrong login or(and) password";
            }
        }
        String page = generatePage(errorMsg);
        response.getWriter().println(page);
        ServletHelper.setEncodingAndOK(response);
    }

    private String generatePage(String errorMsg) throws IOException {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("errorMsg", errorMsg);
        return TemplateHelper.generatePage(TEMPLATE_FILENAME, variables);
    }
}
