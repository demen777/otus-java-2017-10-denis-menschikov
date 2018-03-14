package dm.otus.l15_msg.frontend;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;

@SuppressWarnings("WeakerAccess")
public class TemplateHelper {
    private static final TemplateHelper instance = new TemplateHelper();

    private final Configuration configuration;

    public TemplateHelper() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(TemplateHelper.class, "/template/");
    }

    public static String generatePage(String templateFilename, HashMap<String, Object> variables) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = instance.configuration.getTemplate(templateFilename);
            template.process(variables, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }

    public static String loadStatic(String staticFilename) throws IOException {
        String res = "";
        ClassLoader classLoader = TemplateHelper.class.getClassLoader();
        res = IOUtils.toString(classLoader.getResourceAsStream("/public_html/"+staticFilename));
        return res;
    }
}
