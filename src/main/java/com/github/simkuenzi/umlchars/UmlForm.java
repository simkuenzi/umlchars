package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.*;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.stream.Stream;

public class UmlForm {

    private FormField<String> classNameAField;
    private FormField<String> classNameBField;

    UmlForm() {
        this(new MultivaluedHashMap<>());
    }

    UmlForm(MultivaluedMap<String, String> rawForm) {
        this(
            new MandatoryField(new TextField(new FormValue("classNameA", rawForm))),
            new MandatoryField(new TextField(new FormValue("classNameB", rawForm)))
        );
    }

    private UmlForm(FormField<String> classNameAField, FormField<String> classNameBField) {
        this.classNameAField = classNameAField;
        this.classNameBField = classNameBField;
    }

    UmlForm noValidation() {
        return new UmlForm(
                new AlwaysValidField<>(classNameAField),
                new AlwaysValidField<>(classNameBField)
        );
    }

    public boolean valid() {
        return Stream.of(classNameAField, classNameBField).allMatch(FormField::valid);
    }

    public FormField<String> classNameA() {
        return classNameAField;
    }

    public FormField<String> classNameB() {
        return classNameBField;
    }

    public String message() {
        return valid() ? "" : "Invalid input";
    }
}
