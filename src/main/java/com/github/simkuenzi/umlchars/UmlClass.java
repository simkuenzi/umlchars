package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormField;
import com.github.simkuenzi.restforms.MandatoryField;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.thymeleaf.util.StringUtils.repeat;

public class UmlClass {

    private final MultivaluedMap<String, String> rawForm;
    private final int index;

    public UmlClass(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UmlClass umlClass = (UmlClass) o;
        return index == umlClass.index &&
                Objects.equals(rawForm, umlClass.rawForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawForm, index);
    }

    public FormField<String> getClassName() {
        return new MandatoryField(new TextField("className" + index, rawForm), "Provide some text here");
    }

    public FormField<String> getAttributes() {
        return new TextField("attributes" + index, rawForm);
    }

    public FormField<String> getOperations() {
        return new TextField("operations" + index, rawForm);
    }

    public Shape renderShape() {
        List<String> lines = new ArrayList<>();
        lines.add('+' + repeat('-', width() - 2) + '+');
        lines.add("| " + getClassName().getValue() + repeat(' ', width() - 4 - getClassName().getValue().length()) + " |");
        if (attributeLines().count() > 0) {
            lines.add('|' + repeat('.', width() - 2) + '|');
            attributeLines().forEach(al -> lines.add("| " + al + repeat(' ', width() - 4 - al.length()) + " |"));
        }
        if (operationLines().count() > 0) {
            lines.add('|' + repeat('.', width() - 2) + '|');
            operationLines().forEach(ol -> lines.add("| " + ol + repeat(' ', width() - 4 - ol.length()) + " |"));
        }
        lines.add('+' + repeat('-', width() - 2) + '+');
        return new Shape(x(), y(), lines.toArray(new String[0]));
    }

    int height() {
        return 3 + attributesHeight() + operationsHeight();
    }

    private int operationsHeight() {
        return operationLines().count() > 0 ? (int) (operationLines().count() + 1) : 0;
    }

    private int attributesHeight() {
        return attributeLines().count() > 0 ? (int) (attributeLines().count() + 1) : 0;
    }

    int width() {
        return Stream.concat(
            Stream.of(getClassName().getValue()),
            Stream.concat(
                    attributeLines(),
                    operationLines()
            )
        ).mapToInt(String::length).max().orElse(0) + 4;
    }

    private Stream<String> attributeLines() {
        return getAttributes().getValue().isEmpty() ? Stream.empty() : Arrays.stream(getAttributes().getValue().split("\\r?\\n"));
    }

    private Stream<String> operationLines() {
        return getOperations().getValue().isEmpty() ? Stream.empty() : Arrays.stream(getOperations().getValue().split("\\r?\\n"));
    }

    int x() {
        UmlClassDiagram diagram = new UmlClassDiagram(new HomeForm(rawForm));
        List<UmlClass> classes = new HomeForm(rawForm).getClasses();

        int i = classes.indexOf(this);
        int overlap = diagram.topAssocs()
                .filter(a -> a.getAssocFrom().getValue().equals(getClassName().getValue()))
                .mapToInt(UmlAssociation::fromOverlap)
                .max().orElse(0);
        return i == 0 ? overlap : classes.get(i - 1).x() + classes.get(i - 1).width() + diagram.assocCenter(i - 1)
                .map(UmlAssociation::width).orElse(3);
    }

    int y() {
        return new UmlClassDiagram(new HomeForm(rawForm)).topAssocs().mapToInt(UmlAssociation::heightAtFrom).max().orElse(0);
    }
}