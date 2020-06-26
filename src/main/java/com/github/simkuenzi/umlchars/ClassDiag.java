package com.github.simkuenzi.umlchars;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ClassDiag {

    private List<String> classNames;
    private Collection<Association> associations;

    ClassDiag(List<String> classNames, Collection<Association> associations) {
        this.classNames = classNames;
        this.associations = associations;
    }

    String asText() {
        final StringBuilder sb = new StringBuilder();

        int upperAssocCount = IntStream.range(0, classNames.size()).map(i -> (int) assocsHereToFar(i).count()).sum();

        if (upperAssocCount > 0) {
            for (int i = 0; i < classNames.size(); i++) {
                int boxWidth = classNames.get(i).length() + 4;
                int startX = (boxWidth - 1) / 2;

                if (assocsHereToFar(i).count() > 0) {
                    sb.append(repeat(' ', startX));
                    sb.append("+");
                    sb.append(repeat('-', boxWidth - startX + 2));
                } else if (assocsFarToFar(i).count() > 0) {
                    sb.append(repeat('-', boxWidth + 3));
                } else if (assocsFarToHere(i).count() > 0) {
                    sb.append(repeat('-', startX));
                    sb.append("+");
                    sb.append(repeat(' ', boxWidth - startX - 1));
                } else {
                    sb.append(repeat(' ', boxWidth + 3));
                }
            }
            sb.append("\n");

            for (int i = 0; i < classNames.size(); i++) {
                int boxWidth = classNames.get(i).length() + 4;
                int startX = (boxWidth - 1) / 2;

                if (assocsHereToFar(i).count() > 0) {
                    sb.append(repeat(' ', startX));
                    sb.append("|");
                    sb.append(repeat(' ', boxWidth - startX + 2));
                } else if (assocsFarToHere(i).count() > 0) {
                    sb.append(repeat(' ', startX));
                    sb.append("|");
                    sb.append(repeat(' ', boxWidth - startX - 1));
                } else {
                    sb.append(repeat(' ', boxWidth + 3));
                }
            }
            sb.append("\n");
        }

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

    private Stream<Association> assocsHereToFar(int here) {
        return normalizedAssociation().filter(a -> a.startsFrom(here, classNames)).filter(a -> !a.endsNextFrom(here, classNames));
    }

    private Stream<Association> assocsFarToFar(int here) {
        return normalizedAssociation().filter(a -> a.startsBefore(here, classNames)).filter(a -> a.endsAfter(here, classNames));
    }

    private Stream<Association> assocsFarToHere(int here) {
        return normalizedAssociation().filter(a -> a.ends(here, classNames)).filter(a -> a.startsFarBefore(here, classNames));
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
