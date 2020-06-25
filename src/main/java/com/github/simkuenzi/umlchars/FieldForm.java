package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;

public class FieldForm<T> {
    private FormField<T> field;

    public FieldForm(FormField<T> field) {
        this.field = field;
    }

    public T getValue() {
        return field.value();
    }

    public boolean getValid() {
        return field.valid();
    }

    public String getMessage() {
        return field.message();
    }
}
