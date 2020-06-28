package com.github.simkuenzi.umlchars;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

public class Controller {
    @Context
    private ServletContext servletContext;
    @Context
    private HttpServletRequest servletRequest;
    @Context
    private HttpServletResponse servletResponse;

    private TemplateEngine templateEngine;
    @Context
    private UriInfo uriInfo;

    public Controller(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    protected Response render(String template, Map<String, Object> vars) throws Exception {
        Properties versionProps = new Properties();
        versionProps.load(Controller.class.getResourceAsStream("version.properties"));
        vars.put("version", versionProps.getProperty("version"));
        WebContext context = new WebContext(servletRequest, servletResponse, servletContext, servletRequest.getLocale(), vars);
        return Response.ok(templateEngine.process(template, context), MediaType.TEXT_HTML_TYPE.withCharset("utf-8")).build();
    }

    protected Response redirect(String path) throws URISyntaxException {
        return Response.seeOther(new URI(uriInfo.getBaseUri() + path)).build();
    }
}
