package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;

public class AssocForm {

    private MultivaluedMap<String, String> rawForm;
    private int index;

    public AssocForm(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }

    public FieldForm<String> getAssocFrom() {
        return new FieldForm<>(new TextField(new FormValue("assocFrom" + index, rawForm)));
    }

    public FieldForm<String> getAssocTo() {
        return new FieldForm<>(new TextField(new FormValue("assocTo" + index, rawForm)));
    }
}