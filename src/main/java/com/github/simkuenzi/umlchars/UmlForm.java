package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;
import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.MandatoryField;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UmlForm {

    private MultivaluedMap<String, String> rawForm;

    UmlForm() {
        this(new MultivaluedHashMap<>());
    }

    UmlForm(MultivaluedMap<String, String> rawForm) {
        this.rawForm = rawForm;
    }

    UmlForm noValidation() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.add("classCount", "0");
        return new UmlForm(rawForm);
    }

    public boolean valid() {
        return Stream.concat(Stream.of(classCount()),
                IntStream.range(0, classCount().value()).mapToObj(this::className))
                .allMatch(FormField::valid);
    }

    public FormField<Integer> classCount() {
        return new IntegerField(new FormValue("classCount", rawForm));
    }

    public FormField<String> className(int i) {
        return classNames().get(i);
    }

    public List<FormField<String>> classNames() {
        return IntStream.range(0, classCount().value()).mapToObj(i -> new MandatoryField(new TextField(new FormValue("className" + i, rawForm)), "Provide some text here")).collect(Collectors.toList());
    }

    public String message() {
        return valid() ? "" : "Invalid input";
    }
}
