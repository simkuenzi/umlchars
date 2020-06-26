package com.github.simkuenzi.umlchars;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class ClassDiagTest {

    @Test
    public void helloToWorld() {
        assertEquals("" +
                        "+-------+   +-------+\n" +
                        "| Hello |---| World |\n" +
                        "+-------+   +-------+",
                new ClassDiag(
                        Arrays.asList("Hello", "World"),
                        Collections.singletonList(new Association("Hello", "World"))
                        ).asText());
    }

    @Test
    public void helloToWorldUml() {
        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+",
                new ClassDiag(
                        Arrays.asList("Hello", "World", "UML"),
                        Collections.singletonList(new Association("Hello", "World"))
                ).asText());
    }

    @Test
    public void helloToWorldUmlRevers() {
        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+",
                new ClassDiag(
                        Arrays.asList("Hello", "World", "UML"),
                        Collections.singletonList(new Association("World", "Hello"))
                ).asText());
    }
}