package com.github.simkuenzi.umlchars;

import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class UmlClassTest {
    @Test
    public void classNameOnly() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hi");
        assertEquals("" +
                "+----+\n" +
                "| Hi |\n" +
                "+----+\n",
                stampClass(rawForm));
    }

    @Test
    public void withAttributes() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hi");
        rawForm.putSingle("attributes0", "test\nhello");
        assertEquals("" +
                        "+-------+\n" +
                        "| Hi    |\n" +
                        "|.......|\n" +
                        "| test  |\n" +
                        "| hello |\n" +
                        "+-------+\n",
                stampClass(rawForm));
    }

    @Test
    public void withOperations() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hi");
        rawForm.putSingle("operations0", "bar()\nfoo(param)");
        assertEquals("" +
                        "+------------+\n" +
                        "| Hi         |\n" +
                        "|............|\n" +
                        "| bar()      |\n" +
                        "| foo(param) |\n" +
                        "+------------+\n",
                stampClass(rawForm));
    }

    @Test
    public void withAttributesAndOperations() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hi");
        rawForm.putSingle("attributes0", "test\nhello");
        rawForm.putSingle("operations0", "bar()\nfoo(param)");
        assertEquals("" +
                        "+------------+\n" +
                        "| Hi         |\n" +
                        "|............|\n" +
                        "| test       |\n" +
                        "| hello      |\n" +
                        "|............|\n" +
                        "| bar()      |\n" +
                        "| foo(param) |\n" +
                        "+------------+\n",
                stampClass(rawForm));
    }

    private String stampClass(MultivaluedMap<String, String> rawForm) {
        UmlClass umlClass = new UmlClass(rawForm, 0);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        Shape shape = umlClass.renderShape();
        IntStream.range(0, umlClass.height()).forEach(y -> {
            IntStream.range(0, umlClass.width()).forEach(x ->
                out.print(shape.charAt(x, y))
            );
            out.println();
        });
        out.flush();
        return writer.toString();
    }
}
