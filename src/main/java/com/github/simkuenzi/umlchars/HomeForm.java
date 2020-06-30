package com.github.simkuenzi.umlchars;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeForm {

    private static final Pattern CLASS_NAME_FIELD_PATTERN = Pattern.compile("className(\\d+)");
    private static final Pattern ASSOC_FROM_FIELD_PATTERN = Pattern.compile("assocFrom(\\d+)");

    private final MultivaluedMap<String, String> rawForm;

    HomeForm() {
        this(new MultivaluedHashMap<>());
    }

    HomeForm(MultivaluedMap<String, String> rawForm) {
        this.rawForm = rawForm;
    }

    HomeForm noValidation() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.add("selectedTab", "classes-tab-btn");
        return new HomeForm(rawForm);
    }

    public boolean valid() {
        return getClasses().stream().map(UmlClass::getClassName).allMatch(Field::getValid);
    }

    public List<UmlClass> getClasses() {
        int fieldCount = (int) rawForm.keySet().stream().filter(x -> CLASS_NAME_FIELD_PATTERN.matcher(x).matches()).count();
        return IntStream.range(0, fieldCount).mapToObj(i -> new UmlClass(rawForm, i)).collect(Collectors.toList());
    }

    public List<UmlAssociation> getAssocs() {
        int fieldCount = (int) rawForm.keySet().stream().filter(x -> ASSOC_FROM_FIELD_PATTERN.matcher(x).matches()).count();
        return IntStream.range(0, fieldCount).mapToObj(i -> new UmlAssociation(rawForm, i)).collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public String getSelectedTab() {
        return rawForm.getFirst("selectedTab");
    }

    @SuppressWarnings("unused")
    public String message() {
        return valid() ? "" : "Invalid input";
    }
}
