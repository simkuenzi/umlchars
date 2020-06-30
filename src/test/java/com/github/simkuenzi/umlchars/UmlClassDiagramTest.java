package com.github.simkuenzi.umlchars;

import org.junit.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.junit.Assert.assertEquals;

public class UmlClassDiagramTest {

    @Test
    public void helloToWorld() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "World");
        assertEquals("" +
                        "+-------+   +-------+\n" +
                        "| Hello |---| World |\n" +
                        "+-------+   +-------+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void helloToWorldUml() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "World");
        rawForm.putSingle("className2", "UML");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "World");

        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void helloToWorldUmlRevers() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "World");
        rawForm.putSingle("className2", "UML");
        rawForm.putSingle("assocFrom0", "World");
        rawForm.putSingle("assocTo0", "Hello");
        assertEquals("" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void helloToWorldAndUml() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "World");
        rawForm.putSingle("className2", "UML");
        rawForm.putSingle("assocFrom0", "World");
        rawForm.putSingle("assocTo0", "Hello");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "UML");
        assertEquals("" +
                        "    +----------------------+   \n" +
                        "    |                      |   \n" +
                        "+-------+   +-------+   +-----+\n" +
                        "| Hello |---| World |   | UML |\n" +
                        "+-------+   +-------+   +-----+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void hiToWorldAndCool() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hi");
        rawForm.putSingle("className1", "World");
        rawForm.putSingle("className2", "Cool");
        rawForm.putSingle("assocFrom0", "Hi");
        rawForm.putSingle("assocTo0", "World");
        rawForm.putSingle("assocFrom1", "Hi");
        rawForm.putSingle("assocTo1", "Cool");
        assertEquals("" +
                        "  +---------------------+    \n" +
                        "  |                     |    \n" +
                        "+----+   +-------+   +------+\n" +
                        "| Hi |---| World |   | Cool |\n" +
                        "+----+   +-------+   +------+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void helloMyNiceWorld() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "nice");
        rawForm.putSingle("className3", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFrom1", "my");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "nice");
        rawForm.putSingle("assocTo2", "my");
        assertEquals("" +
                        "              +---------------------+    \n" +
                        "              |                     |    \n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "| Hello |---| my |---| nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+\n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void topAndBottom() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "nice");
        rawForm.putSingle("className3", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFrom1", "my");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "Hello");
        rawForm.putSingle("assocTo2", "nice");
        rawForm.putSingle("assocFrom3", "nice");
        rawForm.putSingle("assocTo3", "my");
        assertEquals("" +
                        "    +-------------------+                \n" +
                        "    |                   |                \n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "| Hello |---| my |---| nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+\n" +
                        "              |                     |    \n" +
                        "              +---------------------+    \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void manyAssocsTop() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "nice");
        rawForm.putSingle("className3", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "nice");
        rawForm.putSingle("assocFrom2", "Hello");
        rawForm.putSingle("assocTo2", "World");
        rawForm.putSingle("assocFrom3", "my");
        rawForm.putSingle("assocTo3", "nice");
        rawForm.putSingle("assocFrom4", "my");
        rawForm.putSingle("assocTo4", "World");
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
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void manyAssocsTopMultiplicity() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "nice");
        rawForm.putSingle("className3", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFromMultiplicity0", "1");
        rawForm.putSingle("assocToMultiplicity0", "1..*");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "nice");
        rawForm.putSingle("assocFromMultiplicity1", "0..1");
        rawForm.putSingle("assocToMultiplicity1", "0..1");
        rawForm.putSingle("assocFrom2", "Hello");
        rawForm.putSingle("assocTo2", "World");
        rawForm.putSingle("assocFromMultiplicity2", "1..*");
        rawForm.putSingle("assocToMultiplicity2", "0..1");
        rawForm.putSingle("assocFrom3", "my");
        rawForm.putSingle("assocTo3", "nice");
        rawForm.putSingle("assocFrom4", "my");
        rawForm.putSingle("assocTo4", "World");
        assertEquals("" +
                        "    +------------------------+                \n" +
                        "    |                        |                \n" +
                        "    |              +---------------------+    \n" +
                        "0..1|              |     0..1|           |    \n" +
                        "+-------+ 1      +----+   +------+   +-------+\n" +
                        "| Hello |--------| my |---| nice |   | World |\n" +
                        "+-------+   1..* +----+   +------+   +-------+\n" +
                        "    |1..*                                |0..1\n" +
                        "    +------------------------------------+    \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }


    @Test
    public void manyAssocs() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "very");
        rawForm.putSingle("className3", "nice");
        rawForm.putSingle("className4", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "Hello");
        rawForm.putSingle("assocTo2", "very");
        rawForm.putSingle("assocFrom3", "my");
        rawForm.putSingle("assocTo3", "very");
        rawForm.putSingle("assocFrom4", "my");
        rawForm.putSingle("assocTo4", "nice");
        rawForm.putSingle("assocFrom5", "my");
        rawForm.putSingle("assocTo5", "World");
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
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void manyAssocsNoCrossing() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "my");
        rawForm.putSingle("className2", "very");
        rawForm.putSingle("className3", "super");
        rawForm.putSingle("className4", "nice");
        rawForm.putSingle("className5", "World");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "my");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "very");
        rawForm.putSingle("assocFrom2", "my");
        rawForm.putSingle("assocTo2", "nice");
        rawForm.putSingle("assocFrom3", "very");
        rawForm.putSingle("assocTo3", "super");
        rawForm.putSingle("assocFrom4", "super");
        rawForm.putSingle("assocTo4", "World");
        assertEquals("" +
                        "    +-------------------+           +----------------------+    \n" +
                        "    |                   |           |                      |    \n" +
                        "+-------+   +----+   +------+   +-------+   +------+   +-------+\n" +
                        "| Hello |---| my |   | very |---| super |   | nice |   | World |\n" +
                        "+-------+   +----+   +------+   +-------+   +------+   +-------+\n" +
                        "              |                                |                \n" +
                        "              +--------------------------------+                \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }


    @Test
    public void multipleAssocsSameDirection() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "Hello");
        rawForm.putSingle("className1", "nice");
        rawForm.putSingle("className2", "World");
        rawForm.putSingle("className3", "!");
        rawForm.putSingle("className4", ":)");
        rawForm.putSingle("assocFrom0", "Hello");
        rawForm.putSingle("assocTo0", "!");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "nice");
        rawForm.putSingle("assocTo2", "Hello");
        rawForm.putSingle("assocFrom3", "nice");
        rawForm.putSingle("assocTo3", "!");
        rawForm.putSingle("assocFrom4", "Hello");
        rawForm.putSingle("assocTo4", ":)");
        assertEquals("" +
                        "    +----------------------------------------+   \n" +
                        "    |                                        |   \n" +
                        "    +--------------------------------+       |   \n" +
                        "    |                                |       |   \n" +
                        "+-------+   +------+   +-------+   +---+   +----+\n" +
                        "| Hello |---| nice |   | World |   | ! |   | :) |\n" +
                        "+-------+   +------+   +-------+   +---+   +----+\n" +
                        "    |          |           |         |           \n" +
                        "    |          +---------------------+           \n" +
                        "    |                      |                     \n" +
                        "    +----------------------+                     \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void multipleFarAssocs() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "A");
        rawForm.putSingle("className1", "B");
        rawForm.putSingle("className2", "C");
        rawForm.putSingle("assocFrom0", "A");
        rawForm.putSingle("assocTo0", "C");
        rawForm.putSingle("assocFrom1", "A");
        rawForm.putSingle("assocTo1", "C");
        assertEquals("" +
                        "  +---------------+  \n" +
                        "  |               |  \n" +
                        "+---+   +---+   +---+\n" +
                        "| A |   | B |   | C |\n" +
                        "+---+   +---+   +---+\n" +
                        "  |               |  \n" +
                        "  +---------------+  \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }

    @Test
    public void multipleAssocsToNext() {
        MultivaluedMap<String, String> rawForm = new MultivaluedHashMap<>();
        rawForm.putSingle("className0", "A");
        rawForm.putSingle("className1", "B");
        rawForm.putSingle("assocFrom0", "A");
        rawForm.putSingle("assocTo0", "B");
        rawForm.putSingle("assocFrom1", "A");
        rawForm.putSingle("assocTo1", "B");
        rawForm.putSingle("assocFrom2", "A");
        rawForm.putSingle("assocTo2", "B");
        assertEquals("" +
                        "  +-------+  \n" +
                        "  |       |  \n" +
                        "+---+   +---+\n" +
                        "| A |---| B |\n" +
                        "+---+   +---+\n" +
                        "  |       |  \n" +
                        "  +-------+  \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
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
        rawForm.putSingle("assocFrom0", "Test");
        rawForm.putSingle("assocTo0", "Hello");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "Test");
        rawForm.putSingle("assocTo2", "World");
        rawForm.putSingle("assocFrom3", "Test");
        rawForm.putSingle("assocTo3", "World");
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
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
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
        rawForm.putSingle("assocFrom0", "Test");
        rawForm.putSingle("assocTo0", "Hello");
        rawForm.putSingle("assocFrom1", "Hello");
        rawForm.putSingle("assocTo1", "World");
        rawForm.putSingle("assocFrom2", "Test");
        rawForm.putSingle("assocTo2", "World");
        rawForm.putSingle("assocFrom3", "Test");
        rawForm.putSingle("assocTo3", "World");
        rawForm.putSingle("assocFrom4", "Hello");
        rawForm.putSingle("assocTo4", "World");
        rawForm.putSingle("assocFrom5", "Hello");
        rawForm.putSingle("assocTo5", "World");
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
                        "+-------+       +-----------+    \n" +
                        "    |                       |    \n" +
                        "    +-----------------------+    \n",
                new UmlClassDiagram(new HomeForm(rawForm)).asText());
    }
}