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

    @Test
    public void helloToWorldAndUml() {
        assertEquals("" +
                        "    +----------------------+   \n" +
                        "    |                      |   \n" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+",
                new ClassDiag(
                        Arrays.asList("Hello", "World", "UML"),
                        Arrays.asList(
                                new Association("World", "Hello"),
                                new Association("Hello", "UML"))
                ).asText());
    }

    @Test
    public void hiToWorldAndCool() {
        assertEquals("" +
                        "  +---------------------+    \n" +
                        "  |                     |    \n" +
                        "+----+   +-------+   +------+\n" +
                        "| Hi |---| World |   | Cool |\n" +
                        "+----+   +-------+   +------+",
                new ClassDiag(
                        Arrays.asList("Hi", "World", "Cool"),
                        Arrays.asList(
                                new Association("Hi", "World"),
                                new Association("Hi", "Cool"))
                ).asText());
    }

    @Test
    public void helloMyNiceWorld() {
        assertEquals("" +
                        "              +---------------------+    \n" +
                        "              |                     |    \n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "| Hello |---| my |---| nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+",
                new ClassDiag(
                        Arrays.asList("Hello", "my", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("my", "World"),
                                new Association("nice", "my"))
                ).asText());
    }
}