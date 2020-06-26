package com.github.simkuenzi.umlchars;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;
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
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        int topAssocCount = IntStream.range(0, classNames.size()).map(i -> (int) intersect(assocsHereToFar(i), farOddAssocs()).count()).sum();
        if (topAssocCount > 0) {
            List<CharStamp> stamps = new ArrayList<>();
            for (int i = 0; i < classNames.size(); i++) {
                int boxWidth = classNames.get(i).length() + 4;
                if (intersect(assocsHereToFar(i), farOddAssocs()).count() > 0) {
                    stamps.add(new TopBegin(boxWidth));
                } else if (intersect(assocsFarToFar(i), farOddAssocs()).count() > 0) {
                    stamps.add(new TopMiddle(boxWidth));
                } else if (intersect(assocsFarToHere(i), farOddAssocs()).count() > 0) {
                    stamps.add(new TopEnd(boxWidth));
                } else {
                    stamps.add(new Empty(boxWidth));
                }
            }
            new StampRow(stamps).stamp(out);
        }

        for (int i = 0; i < classNames.size(); i++) {
            out.print("+");
            out.print(repeat('-', classNames.get(i).length() + 2));
            out.print("+");
            if (i < classNames.size() - 1) {
                out.print("   ");
            } else {
                out.print("\n");
            }
        }
        for (int i = 0; i < classNames.size(); i++) {
            out.print("| ");
            out.print(classNames.get(i));
            out.print(" |");
            if (i < classNames.size() - 1) {
                if (assocExists(classNames.get(i), classNames.get(i + 1))) {
                    out.print("---");
                } else {
                    out.print("   ");
                }
            } else {
                out.print("\n");
            }
        }
        for (int i = 0; i < classNames.size(); i++) {
            out.print("+");
            out.print(repeat('-', classNames.get(i).length() + 2));
            out.append("+");
            if (i < classNames.size() - 1) {
                out.print("   ");
            }
        }
        out.print("\n");

        int bottomAssocCount = IntStream.range(0, classNames.size()).map(i -> (int) intersect(assocsHereToFar(i), farEvenAssocs()).count()).sum();
        if (bottomAssocCount > 0) {
            List<CharStamp> stamps = new ArrayList<>();
            for (int i = 0; i < classNames.size(); i++) {
                int boxWidth = classNames.get(i).length() + 4;
                if (intersect(assocsHereToFar(i), farEvenAssocs()).count() > 0) {
                    stamps.add(new VerticalMirror(new TopBegin(boxWidth)));
                } else if (intersect(assocsFarToFar(i), farEvenAssocs()).count() > 0) {
                    stamps.add(new VerticalMirror(new TopMiddle(boxWidth)));
                } else if (intersect(assocsFarToHere(i), farEvenAssocs()).count() > 0) {
                    stamps.add(new VerticalMirror(new TopEnd(boxWidth)));
                } else {
                    stamps.add(new Empty(boxWidth));
                }
            }
            new StampRow(stamps).stamp(out);
        }


        out.flush();
        return writer.toString();
    }

    private Stream<Association> intersect(Stream<Association> a, Stream<Association> b) {
        return a.filter(b.collect(Collectors.toList())::contains);
    }

    private Stream<Association> assocsHereToFar(int here) {
        return normalizedAssociations().filter(a -> a.startsFrom(here, classNames)).filter(a -> !a.endsNextFrom(here, classNames));
    }

    private Stream<Association> assocsFarToFar(int here) {
        return normalizedAssociations().filter(a -> a.startsBefore(here, classNames)).filter(a -> a.endsAfter(here, classNames));
    }

    private Stream<Association> assocsFarToHere(int here) {
        return normalizedAssociations().filter(a -> a.ends(here, classNames)).filter(a -> a.startsFarBefore(here, classNames));
    }

    private boolean assocExists(String classNameA, String classNameB) {
        return normalizedAssociations().anyMatch(a -> a.equals(new Association(classNameA, classNameB)));
    }

    private Stream<Association> normalizedAssociations() {
        return associations.stream().map(a -> a.normalized(classNames));
    }

    private Stream<Association> farAssocs() {
        return normalizedAssociations().filter(a -> a.isFar(classNames));
    }

    private Stream<Association> farEvenAssocs() {
        List<Association> far = farAssocs().sorted(Comparator.comparing(a -> a.sortOrder(classNames))).collect(Collectors.toList());
        return IntStream.range(0, far.size()).filter(i -> (i+1) % 2 == 0).mapToObj(far::get);
    }

    private Stream<Association> farOddAssocs() {
        List<Association> far = farAssocs().sorted(Comparator.comparing(a -> a.sortOrder(classNames))).collect(Collectors.toList());
        return IntStream.range(0, far.size()).filter(i -> (i+1) % 2 == 1).mapToObj(far::get);
    }

    private String repeat(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}
