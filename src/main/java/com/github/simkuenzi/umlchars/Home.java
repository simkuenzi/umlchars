package com.github.simkuenzi.umlchars;

import org.thymeleaf.TemplateEngine;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("")
public class Home extends Controller {
    Home(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() throws Exception {
        Map<String, Object> vars = new HashMap<>();
        vars.put("form", new UmlForm().noValidation());
        return render("home", vars);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response post(MultivaluedMap<String, String> rawForm) throws Exception {
        UmlForm form = new UmlForm(rawForm);
        Map<String, Object> vars = new HashMap<>();
        vars.put("form", form);
        if (form.valid()) {
            vars.put("uml", new ClassDiag(form).asText());
        }
        return render("home", vars);
    }
}
