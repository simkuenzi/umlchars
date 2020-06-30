package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.thymeleaf.util.StringUtils.repeat;

public class UmlAssociation {
    private final MultivaluedMap<String, String> rawForm;
    private final int index;

    public UmlAssociation(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }

    public Shape renderShape() {
        List<UmlClass> classes = new HomeForm(rawForm).getClasses();
        UmlClass from = classes.stream().filter(c -> c.getClassName().getValue().equals(normalizeFrom())).findFirst().orElseThrow(classRefNotValid(normalizeFrom()));
        UmlClass to = classes.stream().filter(c -> c.getClassName().getValue().equals(normalizeTo())).findFirst().orElseThrow(classRefNotValid(normalizeTo()));
        int x = from.x() + (from.width() - 1) / 2;
        int lineFillerLength = to.x() - x + (to.width() - 1) / 2 - 1;

        if (isCenter()) {
            return new Shape(from.x() + from.width(), from.y() + 1, "---");
        } else if (isTop()) {
            List<String> lines = new ArrayList<>();
            lines.add("+" + repeat('-', lineFillerLength) + "+");
            IntStream.range(1, heightAtFrom()).forEach(i -> lines.add("|" + repeat(' ', lineFillerLength) + '|'));
            return new Shape(x, from.y() - heightAtFrom(), lines.toArray(new String[0]));
        } else {
            List<String> lines = new ArrayList<>();
            int fromTopY = from.y() + from.height();
            int toTopY = to.y() + to.height();
            for (int i = fromTopY; i < toTopY; i++) {
                lines.add("|");
            }
            for (int i = toTopY; i < fromTopY; i++) {
                lines.add(repeat(' ', lineFillerLength + 1) + '|');
            }
            for (int i = Math.max(fromTopY, toTopY); i < bottomY(); i++) {
                lines.add('|' + repeat(' ', lineFillerLength) + '|');
            }
            lines.add('+' + repeat('-', lineFillerLength) + '+');
            return new Shape(x, Math.min(fromTopY, toTopY), lines.toArray(new String[0]));
        }
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

    boolean startsFrom(int here, List<String> classNames) {
        return classNames.get(here).equals(normalizeFrom());
    }

    int sortOrder(List<String> classNames) {
        return classNames.indexOf(normalizeFrom());
    }

    boolean isShort(List<String> classNames) {
        return classNames.indexOf(normalizeTo()) - classNames.indexOf(normalizeFrom()) <= 1;
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
        HomeForm form = new HomeForm(rawForm);
        return IntStream.range(0, form.getClasses().size())
                .filter(i -> form.getClasses().get(i).getClassName().getValue().equals(className))
                .findFirst()
                .orElseThrow(classRefNotValid(className));
    }

    private Supplier<RuntimeException> classRefNotValid(String className) {
        return () -> new RuntimeException(String.format("Association refers to class %s, which is undefined.", className));
    }

    private boolean isCenter() {
        return new UmlClassDiagram(new HomeForm(rawForm)).centerAssocs().anyMatch(this::equals);
    }

    private boolean isTop() {
        return new UmlClassDiagram(new HomeForm(rawForm)).topAssocs().anyMatch(this::equals);
    }

    int heightAtFrom() {
        if (isCenter()) {
            return 1;
        } else if (isTop()) {
            return (int) (new UmlClassDiagram(new HomeForm(rawForm)).topAssocs().filter(this::mustBypass).count() * 2 + 2);
        } else {
            List<UmlClass> classes = new HomeForm(rawForm).getClasses();
            UmlClass from = classes.get(positionOf(normalizeFrom()));
            UmlClass to = classes.get(positionOf(normalizeTo()));
            int maxBottomYBypass = new UmlClassDiagram(new HomeForm(rawForm)).bottomAssocs()
                    .filter(this::mustBypass)
                    .mapToInt(UmlAssociation::bottomY)
                    .max().orElse(0);
            int bottomYFrom = from.y() + from.height() - 1;
            int bottomYTop = to.y() + to.height() - 1;
            return Math.max(Math.max(bottomYFrom, bottomYTop) , maxBottomYBypass) + 2 - from.y() - from.height();
        }
    }

    int bottomY() {
        List<UmlClass> classes = new HomeForm(rawForm).getClasses();
        UmlClass umlClass = classes.get(positionOf(normalizeFrom()));
        if (isCenter()) {
            return umlClass.y() + 1;
        } else if (isTop()) {
            return umlClass.y() - 1;
        } else {
            return umlClass.y() + umlClass.height() + heightAtFrom();
        }
    }

    private boolean mustBypass(UmlAssociation other) {
        return  !other.equals(this) &&
                (
                        positionOf(normalizeFrom()) < positionOf(other.normalizeFrom()) ||
                        positionOf(normalizeFrom()) == positionOf(other.normalizeFrom()) &&
                        positionOf(other.normalizeTo()) < positionOf(normalizeTo())
                ) &&
                positionOf(normalizeTo()) > positionOf(other.normalizeFrom());
    }
}
