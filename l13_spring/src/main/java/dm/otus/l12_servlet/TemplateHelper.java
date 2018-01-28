package dm.otus.l12_servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
}
