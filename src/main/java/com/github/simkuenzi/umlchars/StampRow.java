package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StampRow {
    private List<CharStamp> charStamps;
    private int lineCount;

    public StampRow(List<CharStamp> charStamps) {
        this(charStamps, 2);
    }

    public StampRow(List<CharStamp> charStamps, int lineCount) {
        this.charStamps = charStamps;
        this.lineCount = lineCount;
    }

    public void stamp(PrintWriter out) {
        for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
            for (int i = 0; i < charStamps.size(); i++) {
                charStamps.get(i).stamp(out, lineIndex);
                if (i < charStamps.size() -1) {
                    charStamps.get(i).stampSeparator(out, lineIndex);
                }
            }
            out.println();
        }
    }

    public StampRow combine(CharStamp charStamp, int lineCount) {
        return new StampRow(
                Stream.concat(charStamps.stream(), Stream.of(charStamp)).collect(Collectors.toList()),
                Math.max(this.lineCount, lineCount)
            );
    }
}
