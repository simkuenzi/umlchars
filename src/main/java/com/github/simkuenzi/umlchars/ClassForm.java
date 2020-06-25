package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.MandatoryField;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;

public class ClassForm {

    private MultivaluedMap<String, String> rawForm;
    private int index;

    public ClassForm(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }
    public FieldForm<String> getClassName() {
        return new FieldForm<>(new MandatoryField(new TextField(new FormValue("className" + index, rawForm)), "Provide some text here"));
    }
}