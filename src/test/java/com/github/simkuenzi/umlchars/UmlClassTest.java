package com.github.simkuenzi.umlchars;

import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

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
        StampRow stampRow = new StampRow(Collections.emptyList(), 0);
        stampRow = new UmlClass(rawForm, 0).addToRow(stampRow, false, 0);
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        stampRow.stamp(out);
        out.flush();
        return writer.toString();
    }
}
