package com.github.simkuenzi.umlchars;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClassDiagTest {

    @Test
    public void asText() {
        assertEquals("" +
                "+-------+   +-------+\n" +
                "| Hello |---| World |\n" +
                "+-------+   +-------+",
                new ClassDiag("Hello", "World").asText());
    }
}