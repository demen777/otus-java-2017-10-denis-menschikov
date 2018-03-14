package dm.otus.l15_msg.frontend;

import dm.otus.l15_msg.cache.CacheInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CacheStateServlet extends AbstractHttpServlet {
    public static final String PATH = "cache_state";
    private static final String STATIC_FILENAME = "cache_state.html";

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

    @Override
    public void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String page = TemplateHelper.loadStatic(STATIC_FILENAME);
        response.getWriter().println(page);
        ServletHelper.setEncodingAndOK(response);
    }

}
