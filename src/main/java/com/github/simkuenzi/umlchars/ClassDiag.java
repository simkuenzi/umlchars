package com.github.simkuenzi.umlchars;

import java.util.Arrays;

class ClassDiag {

    private String[] classNames;

    ClassDiag(String... classNames) {
        this.classNames = classNames;
    }

    String asText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classNames.length; i++) {
            sb.append("+").append(repeat('-', classNames[i].length() + 2)).append("+");
            if (i < classNames.length - 1) {
                sb.append("   ");
            } else {
                sb.append("\n");
            }
        }
        for (int i = 0; i < classNames.length; i++) {
            sb.append("| ").append(classNames[i]).append(" |");
            if (i < classNames.length - 1) {
                sb.append("---");
            } else {
                sb.append("\n");
            }
        }
        for (int i = 0; i < classNames.length; i++) {
            sb.append("+").append(repeat('-', classNames[i].length() + 2)).append("+");
            if (i < classNames.length - 1) {
                sb.append("   ");
            }
        }
        return sb.toString();
    }

    private String repeat(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}
