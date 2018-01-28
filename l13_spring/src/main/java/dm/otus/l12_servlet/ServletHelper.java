package dm.otus.l12_servlet;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("WeakerAccess")
public class ServletHelper {

    public static void setEncodingAndOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
