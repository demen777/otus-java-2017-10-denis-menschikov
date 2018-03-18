package dm.otus.l15_msg.frontend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends AbstractHttpServlet {
    public static final String PATH = "";

    @Override
    public void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(CacheStateServlet.PATH);
    }
}
