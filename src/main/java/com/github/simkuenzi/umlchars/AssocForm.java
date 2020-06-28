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

    public Field<String> getAssocFrom() {
        return new Field<>(new TextField(new FormValue("assocFrom" + index, rawForm)));
    }

    public Field<String> getAssocTo() {
        return new Field<>(new TextField(new FormValue("assocTo" + index, rawForm)));
    }
}