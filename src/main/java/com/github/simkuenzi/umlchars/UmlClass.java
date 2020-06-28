package com.github.simkuenzi.umlchars;

import com.github.simkuenzi.restforms.FormValue;
import com.github.simkuenzi.restforms.MandatoryField;
import com.github.simkuenzi.restforms.TextField;

import javax.ws.rs.core.MultivaluedMap;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.thymeleaf.util.StringUtils.repeat;

public class UmlClass {

    private MultivaluedMap<String, String> rawForm;
    private int index;

    public UmlClass(MultivaluedMap<String, String> rawForm, int index) {
        this.rawForm = rawForm;
        this.index = index;
    }
    public Field<String> getClassName() {
        return new Field<>(new MandatoryField(new TextField(new FormValue("className" + index, rawForm)), "Provide some text here"));
    }

    public Field<String> getAttributes() {
        return new Field<>(new TextField(new FormValue("attributes" + index, rawForm)));
    }

    public Field<String> getOperations() {
        return new Field<>(new TextField(new FormValue("operations" + index, rawForm)));
    }

    public StampRow addToRow(StampRow row, boolean connectToBottom, int fullHeight) {
        return row.combine(new CharStamp() {
            @Override
            public void stamp(PrintWriter out, int lineIndex) {
                if (lineIndex == 0 || lineIndex == height() - 1) {
                    out.write('+');
                    out.write(repeat('-', width() - 2));
                    out.write('+');
                } else if (lineIndex == 1) {
                    out.write("| ");
                    out.write(getClassName().getValue());
                    out.write(repeat(' ', width() - 4 - getClassName().getValue().length()));
                    out.write(" |");
                } else if (lineIndex == 2 && attributesHeight() > 0 || lineIndex == attributesHeight() + 2 && operationsHeight() > 0) {
                    out.write('|');
                    out.write(repeat('.', width() - 2));
                    out.write('|');
                } else if (lineIndex > 2 && lineIndex < attributesHeight() + 2) {
                    out.write("| ");
                    String attributeLine = attributeLines().collect(Collectors.toList()).get(lineIndex - 3);
                    out.write(attributeLine);
                    out.write(repeat(' ', width() - 4 - attributeLine.length()));
                    out.write(" |");
                } else if (lineIndex > 2 + attributesHeight() && lineIndex < height()) {
                    out.write("| ");
                    String operationLine = operationLines().collect(Collectors.toList()).get(lineIndex - (3 + attributesHeight()));
                    out.write(operationLine);
                    out.write(repeat(' ', width() - 4 - operationLine.length()));
                    out.write(" |");
                } else if (connectToBottom && lineIndex >= height() && lineIndex < fullHeight) {
                    int startX = (width() - 1) / 2;
                    out.print(repeat(' ', startX));
                    out.print("|");
                    out.print(repeat(' ', width() - startX - 1));
                } else {
                    out.write(repeat(' ', width()));
                }
            }

            @Override
            public void stampSeparator(PrintWriter out, int lineIndex) {

            }
        }, height());
    }

    public CharStamp topBegin() {
        return new TopBegin(width());
    }

    public CharStamp topMiddle() {
        return new TopMiddle(width());
    }

    public CharStamp topEnd() {
        return new TopEnd(width());
    }

    public CharStamp vertical() {
        return new Vertical(width());
    }

    public CharStamp empty() {
        return new Empty(width());
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

    private int width() {
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
}