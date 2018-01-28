package dm.otus.l12_servlet;

import dm.otus.l10_hibernate.CacheInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CacheStateServlet extends AbstractHttpServlet {
    public static final String PATH = "/cache_state";
    private final CacheInfo cacheInfo;
    private final DBWorkEmulator dbWorkEmulator;
    private static final String TEMPLATE_FILENAME = "cache_state.html";

    public CacheStateServlet(AuthService authService, CacheInfo cacheInfo, DBWorkEmulator dbWorkEmulator) {
        super(authService);
        this.cacheInfo = cacheInfo;
        this.dbWorkEmulator = dbWorkEmulator;
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
