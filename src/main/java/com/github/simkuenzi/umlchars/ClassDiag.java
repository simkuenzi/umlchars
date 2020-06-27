package com.github.simkuenzi.umlchars;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Function;
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

        drawRectAssocs(out, topAssocs().collect(Collectors.toList()), TopBegin::new, TopMiddle::new, TopEnd::new, (i, c) -> i);

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
                if (assocCenter(i).isPresent()) {
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

        drawRectAssocs(out, bottomAssocs().collect(Collectors.toList()), w -> new VerticalMirror(new TopBegin(w)),
                w -> new VerticalMirror(new TopMiddle(w)), w -> new VerticalMirror(new TopEnd(w)),
                (i, c) -> c - i - 1);

        out.flush();
        return writer.toString();
    }

    private void drawRectAssocs(PrintWriter out, List<Association> assocs, Function<Integer, CharStamp> begin,
                                Function<Integer, CharStamp> middle, Function<Integer, CharStamp> end, RowOrder order) {
        if (assocs.size() > 0) {
            List<StampRow> rows = new ArrayList<>();
            List<Association> drawnAssocs = new ArrayList<>();

            while (drawnAssocs.size() != assocs.size()) {
                List<CharStamp> stamps = new ArrayList<>();
                boolean assocOpen = false;
                List<Association> drawnHereAssocs = new ArrayList<>();
                for (int i = 0; i < classNames.size(); i++) {
                    final int here = i;
                    int boxWidth = classNames.get(i).length() + 4;
                    List<CharStamp> composite = new ArrayList<>();

                    for (Association a : assocs.stream().filter(x -> x.isFromHere(here, classNames)).collect(Collectors.toList())) {
                        if (drawnAssocs.contains(a)) {
                            composite.add(new Vertical(boxWidth));
                        } else if (!assocOpen) {
                            assocOpen = true;
                            drawnAssocs.add(a);
                            drawnHereAssocs.add(a);
                            composite.add(begin.apply(boxWidth));
                        }
                    }

                    for (Association a : assocs.stream().filter(x -> x.isToHere(here, classNames)).collect(Collectors.toList())) {
                        if(drawnHereAssocs.contains(a)) {
                            composite.add(end.apply(boxWidth));
                            assocOpen = false;
                        } else if (drawnAssocs.contains(a)) {
                            composite.add(new Vertical(boxWidth));
                        }
                    }

                    for (Association a : assocs.stream().filter(x -> x.isThroughHere(here, classNames)).filter(drawnHereAssocs::contains).collect(Collectors.toList())) {
                        composite.add(middle.apply(boxWidth));
                    }
                    composite.add(new Empty(boxWidth));
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
        return normalizedAssociations().filter(a -> a.startsFrom(here, classNames)).filter(a -> !a.isFar(classNames));
    }

    private Stream<Association> normalizedAssociations() {
        return associations.stream().map(a -> a.normalized(classNames));
    }


    private Stream<Association> centerAssocs() {
        return IntStream.range(0, classNames.size()).mapToObj(this::assocCenter).filter(Optional::isPresent).map(Optional::get);
    }

    private Stream<Association> rectAssocs() {
        List<Association> center = centerAssocs().collect(Collectors.toList());
        return normalizedAssociations().filter(a -> !center.contains(a)).sorted(Comparator.comparing(a -> a.sortOrder(classNames)));
    }

    private Stream<Association> bottomAssocs() {
        List<Association> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 0).mapToObj(rect::get);
    }

    private Stream<Association> topAssocs() {
        List<Association> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 1).mapToObj(rect::get);
    }

    private String repeat(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    private interface RowOrder {
        int sortKey(int i, int size);
    }
}
