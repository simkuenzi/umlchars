package com.github.simkuenzi.umlchars;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UmlForm {

    private static final Pattern CLASS_NAME_FIELD_PATTERN = Pattern.compile("className(\\d+)");
    private static final Pattern ASSOC_FROM_FIELD_PATTERN = Pattern.compile("assocFrom(\\d+)");

    private MultivaluedMap<String, String> rawForm;

    UmlForm() {
        this(new MultivaluedHashMap<>());
    }

    UmlForm(MultivaluedMap<String, String> rawForm) {
        this.rawForm = rawForm;
    }

    UmlForm noValidation() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.add("selectedTab", "classes-tab-btn");
        return new UmlForm(rawForm);
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

    public String getSelectedTab() {
        return rawForm.getFirst("selectedTab");
    }

    public String message() {
        return valid() ? "" : "Invalid input";
    }
}
