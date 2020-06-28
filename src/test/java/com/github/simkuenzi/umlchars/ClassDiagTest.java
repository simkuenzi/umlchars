package com.github.simkuenzi.umlchars;

import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ClassDiagTest {

    @Test
    public void helloToWorld() {
        assertEquals("" +
                        "+-------+   +-------+\n" +
                        "| Hello |---| World |\n" +
                        "+-------+   +-------+\n",
                new ClassDiag(
                        classes("Hello", "World"),
                        Collections.singletonList(new Association("Hello", "World"))
                        ).asText());
    }

    @Test
    public void helloToWorldUml() {
        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+\n",
                new ClassDiag(
                        classes("Hello", "World", "UML"),
                        Collections.singletonList(new Association("Hello", "World"))
                ).asText());
    }

    @Test
    public void helloToWorldUmlRevers() {
        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+\n",
                new ClassDiag(
                        classes("Hello", "World", "UML"),
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
                        "+-------+   +-------+   +-----+\n",
                new ClassDiag(
                        classes("Hello", "World", "UML"),
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
                        "+----+   +-------+   +------+\n",
                new ClassDiag(
                        classes("Hi", "World", "Cool"),
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
                        "+-------+   +----+   +------+   +-------+\n",
                new ClassDiag(
                        classes("Hello", "my", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("my", "World"),
                                new Association("nice", "my"))
                ).asText());
    }

    @Test
    public void topAndBottom() {
        assertEquals("" +
                        "    +-------------------+                \n" +
                        "    |                   |                \n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "| Hello |---| my |---| nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "              |                     |    \n" +
                        "              +---------------------+    \n",
                new ClassDiag(
                        classes("Hello", "my", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("my", "World"),
                                new Association("Hello", "nice"),
                                new Association("nice", "my"))
                ).asText());
    }

    @Test
    public void manyAssocsTop() {
        assertEquals("" +
                        "    +-------------------+                \n" +
                        "    |                   |                \n" +
                        "    |         +---------------------+    \n" +
                        "    |         |         |           |    \n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "| Hello |---| my |---| nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "    |                               |    \n" +
                        "    +-------------------------------+    \n",
                new ClassDiag(
                        classes("Hello", "my", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("Hello", "nice"),
                                new Association("Hello", "World"),
                                new Association("my", "nice"),
                                new Association("my", "World"))
                ).asText());
    }


    @Test
    public void manyAssocs() {
        assertEquals("" +
                        "    +------------------------------------------+    \n" +
                        "    |                                          |    \n" +
                        "    |         +--------------------+           |    \n" +
                        "    |         |                    |           |    \n" +
                        "+-------+   +----+   +------+   +------+   +-------+\n" +
                        "| Hello |---| my |---| very |   | nice |   | World |\n" +
                        "+-------+   +----+   +------+   +------+   +-------+\n" +
                        "    |         |         |                      |    \n" +
                        "    |         +--------------------------------+    \n" +
                        "    |                   |                           \n" +
                        "    +-------------------+                           \n",
                new ClassDiag(
                        classes("Hello", "my", "very", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("Hello", "World"),
                                new Association("Hello", "very"),
                                new Association("my", "very"),
                                new Association("my", "nice"),
                                new Association("my", "World"))
                ).asText());
    }

    @Test
    public void manyAssocsNoCrossing() {
        assertEquals("" +
                        "    +-------------------+           +----------------------+    \n" +
                        "    |                   |           |                      |    \n" +
                        "+-------+   +----+   +------+   +-------+   +------+   +-------+\n" +
                        "| Hello |---| my |   | very |---| super |   | nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+   +------+   +-------+\n" +
                        "              |                                |                \n" +
                        "              +--------------------------------+                \n",
                new ClassDiag(
                        classes("Hello", "my", "very", "super", "nice", "World"),
                        Arrays.asList(
                                new Association("Hello", "my"),
                                new Association("Hello", "very"),
                                new Association("my", "nice"),
                                new Association("very", "super"),
                                new Association("super", "World"))
                ).asText());
    }


    @Test
    public void multipleAssocsSameDirection() {
        assertEquals("" +
                        "    +--------------------------------+           \n" +
                        "    |                                |           \n" +
                        "    +----------------------------------------+   \n" +
                        "    |                                |       |   \n" +
                        "+-------+   +------+   +-------+   +---+   +----+\n" +
                        "| Hello |---| nice |   | World |   | ! |   | :) |\n" +
                        "+-------+   +------+   +-------+   +---+   +----+\n" +
                        "    |          |           |         |           \n" +
                        "    |          +---------------------+           \n" +
                        "    |                      |                     \n" +
                        "    +----------------------+                     \n",
                new ClassDiag(
                        classes("Hello", "nice", "World", "!", ":)"),
                        Arrays.asList(
                                new Association("Hello", "!"),
                                new Association("Hello", "World"),
                                new Association("nice", "Hello"),
                                new Association("nice", "!"),
                                new Association("Hello", ":)"))
                ).asText());
    }

    @Test
    public void multipleFarAssocs() {
        assertEquals("" +
                        "  +---------------+  \n" +
                        "  |               |  \n" +
                        "+---+   +---+   +---+\n" +
                        "| A |   | B |   | C |\n" +
                        "+---+   +---+   +---+\n" +
                        "  |               |  \n" +
                        "  +---------------+  \n",
                new ClassDiag(
                        classes("A", "B", "C"),
                        Arrays.asList(
                                new Association("A", "C"),
                                new Association("A", "C"))
                ).asText());
    }

    @Test
    public void multipleAssocsToNext() {
        assertEquals("" +
                        "  +-------+  \n" +
                        "  |       |  \n" +
                        "+---+   +---+\n" +
                        "| A |---| B |\n" +
                        "+---+   +---+\n" +
                        "  |       |  \n" +
                        "  +-------+  \n",
                new ClassDiag(
                        classes("A", "B"),
                        Arrays.asList(
                                new Association("A", "B"),
                                new Association("A", "B"),
                                new Association("A", "B"))
                ).asText());
    }

    @Test
    public void differentHeightOfClasses() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Test");
        rawForm.putSingle("attributes0", "hello");
        rawForm.putSingle("operations0", "foo()");
        rawForm.putSingle("className1", "Hello");
        rawForm.putSingle("attributes1", "");
        rawForm.putSingle("operations1", "");
        rawForm.putSingle("className2", "World");
        rawForm.putSingle("attributes2", "");
        rawForm.putSingle("operations2", "do()");

        assertEquals("" +
                        "    +-----------------------+    \n" +
                        "    |                       |    \n" +
                        "+-------+   +-------+   +-------+\n" +
                        "| Test  |---| Hello |---| World |\n" +
                        "|.......|   +-------+   |.......|\n" +
                        "| hello |               | do()  |\n" +
                        "|.......|               +-------+\n" +
                        "| foo() |                   |    \n" +
                        "+-------+                   |    \n" +
                        "    |                       |    \n" +
                        "    +-----------------------+    \n",
                new ClassDiag(
                        IntStream.range(0, 3).mapToObj(i -> new UmlClass(rawForm, i)).collect(Collectors.toList()),
                        Arrays.asList(
                                new Association("Test", "Hello"),
                                new Association("Hello", "World"),
                                new Association("Test", "World"),
                                new Association("Test", "World"))
                ).asText());
    }

    @Test
    public void connectToBotton() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Test");
        rawForm.putSingle("attributes0", "hello");
        rawForm.putSingle("operations0", "foo()");
        rawForm.putSingle("className1", "Hello");
        rawForm.putSingle("attributes1", "");
        rawForm.putSingle("operations1", "");
        rawForm.putSingle("className2", "World");
        rawForm.putSingle("attributes2", "");
        rawForm.putSingle("operations2", "do()");

        assertEquals("" +
                        "    +-----------------------+    \n" +
                        "    |                       |    \n" +
                        "    |           +-----------+    \n" +
                        "    |           |           |    \n" +
                        "+-------+   +-------+   +-------+\n" +
                        "| Test  |---| Hello |---| World |\n" +
                        "|.......|   +-------+   |.......|\n" +
                        "| hello |       |       | do()  |\n" +
                        "|.......|       |       +-------+\n" +
                        "| foo() |       |           |    \n" +
                        "+-------+       |           |    \n" +
                        "    |           |           |    \n" +
                        "    |           +-----------+    \n" +
                        "    |                       |    \n" +
                        "    +-----------------------+    \n",
                new ClassDiag(
                        IntStream.range(0, 3).mapToObj(i -> new UmlClass(rawForm, i)).collect(Collectors.toList()),
                        Arrays.asList(
                                new Association("Test", "Hello"),
                                new Association("Hello", "World"),
                                new Association("Test", "World"),
                                new Association("Test", "World"),
                                new Association("Hello", "World"),
                                new Association("Hello", "World"))
                ).asText());
    }

    private List<UmlClass> classes(String... classNames) {
        return Arrays.stream(classNames).map(x -> {
            MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
            rawForm.putSingle("className0", x);
            return new UmlClass(rawForm, 0);
        }).collect(Collectors.toList());
    }
}