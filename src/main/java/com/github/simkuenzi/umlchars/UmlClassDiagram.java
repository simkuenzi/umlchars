package com.github.simkuenzi.umlchars;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class UmlClassDiagram {

    private final HomeForm form;

    public UmlClassDiagram(HomeForm form) {
        this.form = form;
    }

    String asText() {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);

        List<Shape> shapes = new ArrayList<>();
        form.getClasses().forEach(c -> shapes.add(c.renderShape()));
        centerAssocs().forEach(a -> shapes.add(a.renderShape()));
        topAssocs().sorted(Comparator.comparing(UmlAssociation::heightAtFrom).reversed()).forEach(a -> shapes.add(a.renderShape()));
        bottomAssocs().sorted(Comparator.comparing(UmlAssociation::bottomY).reversed()).forEach(a -> shapes.add(a.renderShape()));

        UmlClass last = form.getClasses().get(form.getClasses().size() - 1);
        int width = last.x() + last.width();
        int assocMaxBottomY = form.getAssocs().stream().mapToInt(UmlAssociation::bottomY).max().orElse(0);
        int classMaxBottomY = form.getClasses().stream().mapToInt(c -> c.y() + c.height() - 1).max().orElse(0);
        int height = Math.max(assocMaxBottomY, classMaxBottomY) + 1;

        IntStream.range(0, height).forEach(y -> {
            IntStream.range(0, width).forEach(x ->
                    out.print(IntStream.range(0, shapes.size())
                    .mapToObj(i -> shapes.get(shapes.size() - i - 1))
                    .map(s -> s.charAt(x, y))
                    .filter(c -> !Character.isWhitespace(c))
                    .findFirst()
                    .orElse(' ')));
            out.println();
        });

        out.flush();
        return writer.toString();
    }

    Stream<UmlAssociation> centerAssocs() {
        return IntStream.range(0, form.getClasses().size()).mapToObj(this::assocCenter).filter(Optional::isPresent).map(Optional::get);
    }

    Stream<UmlAssociation> topAssocs() {
        List<UmlAssociation> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 1).mapToObj(rect::get);
    }

    Stream<UmlAssociation> bottomAssocs() {
        List<UmlAssociation> rect = rectAssocs().collect(Collectors.toList());
        return IntStream.range(0, rect.size()).filter(i -> (i+1) % 2 == 0).mapToObj(rect::get);
    }

    Optional<UmlAssociation> assocCenter(int here) {
        return assocsHereToNext(here).findFirst();
    }

    private Stream<UmlAssociation> assocsHereToNext(int here) {
        return form.getAssocs().stream().filter(a -> a.startsFrom(here, classNames())).filter(a -> a.isShort(classNames()));
    }

    private Stream<UmlAssociation> rectAssocs() {
        List<UmlAssociation> center = centerAssocs().collect(Collectors.toList());
        return form.getAssocs().stream().filter(a -> !center.contains(a)).sorted(Comparator.comparing(a -> a.sortOrder(classNames())));
    }

    private List<String> classNames() {
        return form.getClasses().stream().map(x -> x.getClassName().getValue()).collect(Collectors.toList());
    }
}
