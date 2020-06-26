package com.github.simkuenzi.umlchars;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

class ClassDiag {

    private List<String> classNames;
    private Collection<Association> associations;

    ClassDiag(List<String> classNames, Collection<Association> associations) {
        this.classNames = classNames;
        this.associations = associations;
    }

    String asText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classNames.size(); i++) {
            sb.append("+").append(repeat('-', classNames.get(i).length() + 2)).append("+");
            if (i < classNames.size() - 1) {
                sb.append("   ");
            } else {
                sb.append("\n");
            }
        }
        for (int i = 0; i < classNames.size(); i++) {
            sb.append("| ").append(classNames.get(i)).append(" |");
            if (i < classNames.size() - 1) {
                if (assocExists(classNames.get(i), classNames.get(i + 1))) {
                    sb.append("---");
                } else {
                    sb.append("   ");
                }
            } else {
                sb.append("\n");
            }
        }
        for (int i = 0; i < classNames.size(); i++) {
            sb.append("+").append(repeat('-', classNames.get(i).length() + 2)).append("+");
            if (i < classNames.size() - 1) {
                sb.append("   ");
            }
        }
        return sb.toString();
    }

    private boolean assocExists(String classNameA, String classNameB) {
        return normalizedAssociation().anyMatch(a -> a.equals(new Association(classNameA, classNameB)));
    }

    private Stream<Association> normalizedAssociation() {
        return associations.stream().map(a -> a.normalized(classNames));
    }

    private String repeat(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}
