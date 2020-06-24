package com.github.simkuenzi.umlchars;

import java.util.Arrays;

class ClassDiag {

    private String classNameA;
    private String classNameB;

    ClassDiag(String classNameA, String classNameB) {
        this.classNameA = classNameA;
        this.classNameB = classNameB;
    }

    String asText() {
        return
                "+" + repeat('-', classNameA.length() + 2) + "+" + "   " + "+" + repeat('-', classNameB.length() + 2) + "+\n" +
                "| " + classNameA + " |---| " + classNameB + " |\n" +
                "+" + repeat('-', classNameA.length() + 2) + "+" + "   " + "+" + repeat('-', classNameB.length() + 2) + "+";
    }

    private String repeat(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}
