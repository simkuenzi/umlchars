package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class UmlAssociation {
    private MultivaluedMap<String, String> rawForm;
    private int index;

    public UmlAssociation(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UmlAssociation that = (UmlAssociation) o;
        return index == that.index &&
                Objects.equals(rawForm, that.rawForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawForm, index);
    }

    public Field<String> getAssocFrom() {
        return new Field<>(new TextField(new FormValue("assocFrom" + index, rawForm)));
    }

    public Field<String> getAssocTo() {
        return new Field<>(new TextField(new FormValue("assocTo" + index, rawForm)));
    }

    public boolean startsFrom(int here, List<String> classNames) {
        return classNames.get(here).equals(normalizeFrom());
    }

    public int sortOrder(List<String> classNames) {
        return classNames.indexOf(normalizeFrom());
    }

    public boolean isFar(List<String> classNames) {
        return classNames.indexOf(normalizeTo()) - classNames.indexOf(normalizeFrom()) > 1;
    }

    public boolean isFromHere(int here, List<String> classNames) {
        return classNames.indexOf(normalizeFrom()) == here;
    }

    public boolean isToHere(int here, List<String> classNames) {
        return classNames.indexOf(normalizeTo()) == here;
    }

    public boolean isThroughHere(int here, List<String> classNames) {
        return classNames.indexOf(normalizeFrom()) < here && classNames.indexOf(normalizeTo()) > here;
    }

    private String normalizeFrom() {
        return isNormalized() ? getAssocFrom().getValue() : getAssocTo().getValue();
    }

    private String normalizeTo() {
        return isNormalized() ? getAssocTo().getValue() : getAssocFrom().getValue();
    }

    private boolean isNormalized() {
        return positionOf(getAssocFrom().getValue()) < positionOf(getAssocTo().getValue());
    }

    private int positionOf(String className) {
        UmlForm form = new UmlForm(rawForm);
        return IntStream.range(0, form.getClasses().size())
                .filter(i -> form.getClasses().get(i).getClassName().getValue().equals(className))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Association refers to class %s, which is undefined.", className)));
    }
}
