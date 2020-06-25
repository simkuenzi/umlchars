package com.github.simkuenzi.umlchars;

import org.thymeleaf.TemplateEngine;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("")
public class Home extends Controller {
    Home(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get() throws Exception {
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
            vars.put("uml", new ClassDiag(IntStream.range(0, form.classCount().value()).mapToObj(i -> form.className(i).rawValue()).collect(Collectors.toList()), Collections.emptyList()).asText());
        }
        return Response.ok(render("home", vars), MediaType.TEXT_HTML).build();
    }
}
