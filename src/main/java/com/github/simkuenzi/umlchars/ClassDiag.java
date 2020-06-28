package com.github.simkuenzi.umlchars;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.thymeleaf.util.StringUtils.repeat;

class ClassDiag {

    private List<UmlClass> classes;
    private Collection<Association> associations;

    ClassDiag(List<UmlClass> classes, Collection<Association> associations) {
        this.classes = classes;
        this.associations = associations;
    }

    String asText() {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        drawRectAssocs(out, topAssocs().collect(Collectors.toList()), UmlClass::topBegin, UmlClass::topMiddle,
                UmlClass::topEnd, (i, c) -> i);

        StampRow stampRow = new StampRow(Collections.emptyList(), 0);
        for (int i = 0; i < classes.size(); i++) {
            final int classIndex = i;
            stampRow = classes.get(i).addToRow(
                    stampRow,
                    bottomAssocs().anyMatch(a -> a.isToHere(classIndex, classNames()) || a.isFromHere(classIndex, classNames())),
                    classes.stream().mapToInt(UmlClass::height).max().orElse(0));
            if (i < classes.size() - 1) {
                if (assocCenter(i).isPresent()) {
                    stampRow = stampRow.combine(new Horizontal(), 2);
                } else {
                    stampRow = stampRow.combine(new CharStamp() {
                        @Override
                        public void stamp(PrintWriter out, int lineIndex) {
                            out.write(repeat(' ', 3));
                        }

                        @Override
                        public void stampSeparator(PrintWriter out, int lineIndex) {

                        }
                    }, 2);
                }
            }
        }
        stampRow.stamp(out);

        drawRectAssocs(out, bottomAssocs().collect(Collectors.toList()), c -> new VerticalMirror(c.topBegin()),
                c -> new VerticalMirror(c.topMiddle()), c -> new VerticalMirror(c.topEnd()),
                (i, c) -> c - i - 1);

        out.flush();
        return writer.toString();
    }

    private void drawRectAssocs(PrintWriter out, List<Association> assocs, Function<UmlClass, CharStamp> begin,
                                Function<UmlClass, CharStamp> middle, Function<UmlClass, CharStamp> end, RowOrder order) {
        if (assocs.size() > 0) {
            List<StampRow> rows = new ArrayList<>();
            List<Association> drawnAssocs = new ArrayList<>();

            while (drawnAssocs.size() != assocs.size()) {
                List<CharStamp> stamps = new ArrayList<>();
                boolean assocOpen = false;
                List<Association> drawnHereAssocs = new ArrayList<>();
                for (int i = 0; i < classes.size(); i++) {
                    final int here = i;
                    UmlClass umlClass = classes.get(i);
                    List<CharStamp> composite = new ArrayList<>();

                    for (Association a : assocs.stream().filter(x -> x.isFromHere(here, classNames())).collect(Collectors.toList())) {
                        if (drawnAssocs.contains(a)) {
                            composite.add(umlClass.vertical());
                        } else if (!assocOpen) {
                            assocOpen = true;
                            drawnAssocs.add(a);
                            drawnHereAssocs.add(a);
                            composite.add(begin.apply(umlClass));
                        }
                    }

                    for (Association a : assocs.stream().filter(x -> x.isToHere(here, classNames())).collect(Collectors.toList())) {
                        if(drawnHereAssocs.contains(a)) {
                            composite.add(end.apply(umlClass));
                            assocOpen = false;
                        } else if (drawnAssocs.contains(a)) {
                            composite.add(umlClass.vertical());
                        }
                    }

                    for (Association a : assocs.stream().filter(x -> x.isThroughHere(here, classNames())).filter(drawnHereAssocs::contains).collect(Collectors.toList())) {
                        composite.add(middle.apply(umlClass));
                    }
                    composite.add(umlClass.empty());
                    stamps.add(new CompositeStamp(composite));
                }

                rows.add(new StampRow(stamps));
            }

            IntStream.range(0, rows.size()).map(i -> order.sortKey(i, rows.size())).mapToObj(rows::get).forEach(r -> r.stamp(out));
        }
    }

    private Optional<Association> assocCenter(int here) {
        return assocsHereToNext(here).findFirst();
    }

    private Stream<Association> assocsHereToNext(int here) {
        return normalizedAssociations().filter(a -> a.startsFrom(here, classNames())).filter(a -> !a.isFar(classNames()));
    }

    private Stream<Association> normalizedAssociations() {
        return associations.stream().map(a -> a.normalized(classNames()));
    }


    private Stream<Association> centerAssocs() {
        return IntStream.range(0, classes.size()).mapToObj(this::assocCenter).filter(Optional::isPresent).map(Optional::get);
    }

    private Stream<Association> rectAssocs() {
        List<Association> center = centerAssocs().collect(Collectors.toList());
        return normalizedAssociations().filter(a -> !center.contains(a)).sorted(Comparator.comparing(a -> a.sortOrder(classNames())));
    }

    private Stream<Association> bottomAssocs() {
        List<Association> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 0).mapToObj(rect::get);
    }

    private Stream<Association> topAssocs() {
        List<Association> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 1).mapToObj(rect::get);
    }

    private List<String> classNames() {
        return classes.stream().map(x -> x.getClassName().getValue()).collect(Collectors.toList());
    }

    private interface RowOrder {
        int sortKey(int i, int size);
    }
}
