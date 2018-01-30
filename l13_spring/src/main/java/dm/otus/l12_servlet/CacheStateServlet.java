package dm.otus.l12_servlet;

import dm.otus.l10_hibernate.CacheInfo;
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
    @Autowired
    private CacheInfo cacheInfo;
    @Autowired
    private DBWorkEmulator dbWorkEmulator;
    private static final String TEMPLATE_FILENAME = "cache_state.html";

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

    @Override
    public void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        dbWorkEmulator.doSomeWork();
        String page = generatePage(cacheInfo.getHitCount(), cacheInfo.getMissCount());
        response.getWriter().println(page);
        ServletHelper.setEncodingAndOK(response);
    }

    private String generatePage(long hitCount, long missCount) throws IOException {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("hits", hitCount);
        variables.put("misses", missCount);
        return TemplateHelper.generatePage(TEMPLATE_FILENAME, variables);
    }
}
