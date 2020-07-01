package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;
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
            List<String> lines = new ArrayList<>();
            lines.add(" " + normalizeFromMultiplicity());
            lines.add(repeat('-', width()));
            lines.add(repeat(' ', normalizeFromMultiplicity().length() + 2) + normalizeToMultiplicity());
            return new Shape(from.x() + from.width(), from.y(), lines.toArray(new String[0]));
        } else if (isTop()) {
            List<String> lines = new ArrayList<>();
            String prefix = repeat(' ', normalizeFromMultiplicity().length());
            lines.add(prefix + "+" + repeat('-', lineFillerLength) + "+");
            IntStream.range(1, heightAtFrom() - 1).forEach(i -> lines.add(prefix + "|" + repeat(' ', lineFillerLength) + '|'));
            lines.add(normalizeFromMultiplicity() + "|" + repeat(' ', lineFillerLength - normalizeToMultiplicity().length()) + normalizeToMultiplicity() + "|");
            return new Shape(x - normalizeFromMultiplicity().length(), from.y() - heightAtFrom(), lines.toArray(new String[0]));
        } else {
            List<String> lines = new ArrayList<>();
            int fromTopY = from.y() + from.height();
            int toTopY = to.y() + to.height();
            boolean fromMultWritten = false;
            boolean toMultWritten = false;
            for (int i = fromTopY; i < toTopY; i++) {
                lines.add(bottomVerticalSegment('|', fromMultWritten ? "" : normalizeFromMultiplicity(), lineFillerLength, ' ', ""));
                fromMultWritten = true;
            }
            for (int i = toTopY; i < fromTopY; i++) {
                lines.add(bottomVerticalSegment(' ', "", lineFillerLength, '|', toMultWritten ? "" : normalizeToMultiplicity()));
                toMultWritten = true;
            }
            for (int i = Math.max(fromTopY, toTopY); i < bottom(); i++) {
                lines.add(bottomVerticalSegment('|',  fromMultWritten ? "" : normalizeFromMultiplicity() , lineFillerLength, '|', toMultWritten ? "" : normalizeToMultiplicity()));
                fromMultWritten = true;
                toMultWritten = true;
            }
            lines.add('+' + repeat('-', lineFillerLength) + '+');
            return new Shape(x, Math.min(fromTopY, toTopY), lines.toArray(new String[0]));
        }
    }

    private String bottomVerticalSegment(char left, String leftLabel, int fillerLength, char right, String rightLabel) {
        return left + leftLabel + repeat(' ', fillerLength - leftLabel.length())  + right + rightLabel;
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

    public FormField<String> getAssocFrom() {
        return new TextField("assocFrom" + index, rawForm);
    }

    public FormField<String> getAssocFromMultiplicity() {
        return new TextField("assocFromMultiplicity" + index, rawForm);
    }

    public FormField<String> getAssocTo() {
        return new TextField("assocTo" + index, rawForm);
    }

    public FormField<String> getAssocToMultiplicity() {
        return new TextField("assocToMultiplicity" + index, rawForm);
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

    private String normalizeFromMultiplicity() {
        return isNormalized() ? getAssocFromMultiplicity().getValue() : getAssocToMultiplicity().getValue();
    }

    private String normalizeToMultiplicity() {
        return isNormalized() ? getAssocToMultiplicity().getValue() : getAssocFromMultiplicity().getValue();
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
            int maxBottomYBypass = new UmlClassDiagram(new HomeForm(rawForm)).bottomAssocs()
                    .filter(this::mustBypass)
                    .mapToInt(UmlAssociation::bottom)
                    .max().orElse(0);
            int bottomClasses = IntStream.range(positionOf(normalizeFrom()), positionOf(normalizeTo()) + 1)
                    .mapToObj(classes::get)
                    .mapToInt(c -> c.y() + c.height() - 1)
                    .max().orElse(0);
            return Math.max(bottomClasses , maxBottomYBypass) + 2 - from.y() - from.height();
        }
    }

    int bottom() {
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

    int left() {
        List<UmlClass> classes = new HomeForm(rawForm).getClasses();
        UmlClass umlClass = classes.get(positionOf(normalizeTo()));
        return (umlClass.width() - 1) / 2 + normalizeToMultiplicity().length() + umlClass.x();
    }

    int width() {
        if (isCenter()) {
            return  normalizeFromMultiplicity().length() + normalizeToMultiplicity().length() + 3;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    int fromOverlap() {
        List<UmlClass> classes = new HomeForm(rawForm).getClasses();
        return Math.max(0, -((classes.get(positionOf(normalizeFrom())).width() - 1) / 2 - normalizeFromMultiplicity().length()));
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
