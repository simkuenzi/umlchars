package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;
import com.github.simkuenzi.restforms.FormValue;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class IntegerField implements FormField<Integer> {

    private static final Pattern PATTERN = Pattern.compile("\\d+");

    private FormValue value;

    public IntegerField(FormValue value) {
        this.value = value;
    }

    @NotNull
    @Override
    public String message() {
        return "";
    }

    @NotNull
    @Override
    public String rawValue() {
        return value.rawValue();
    }

    @Override
    public boolean valid() {
        return PATTERN.matcher(rawValue()).matches();
    }

    @Override
    public Integer value() {
        return Integer.parseInt(rawValue());
    }

    @NotNull
    @Override
    public String name() {
        // TODO Take from field value
        return "classCount";
    }

    @Override
    public String toString() {
        return Integer.toString(value());
    }
}
