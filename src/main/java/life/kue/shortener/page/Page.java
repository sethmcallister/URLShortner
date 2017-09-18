package life.kue.shortener.page;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

public abstract class Page {

    public String render(Request request, Map<String, Object> model, String templatePath)
    {
        model.put("WebPath", Web.class); // Access application URLs from templates
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }


    private VelocityTemplateEngine strictVelocityEngine()
    {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }

    public static final class Web
    {
        public static final String ADD = "/add/:url";
        public static final String TO = "/to/:url";
    }

    public static class Template
    {
        public static final String ADD = "/web/add/add.vm";
        public static final String TO = "/web/to/to.vm";
    }
}
