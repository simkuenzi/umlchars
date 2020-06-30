package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;

public class Field<T> {
    private FormField<T> field;

    public Field(FormField<T> field) {
        this.field = field;
    }

    public T getValue() {
        return field.value();
    }

    public boolean getValid() {
        return field.valid();
    }

    @SuppressWarnings("unused")
    public String getMessage() {
        return field.message();
    }
}
