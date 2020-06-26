package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;
import java.util.List;

public class StampRow {
    private List<CharStamp> charStamps;

    public StampRow(List<CharStamp> charStamps) {
        this.charStamps = charStamps;
    }

    public void stamp(PrintWriter out) {
        for (int lineIndex = 0; lineIndex < 2; lineIndex++) {
            for (int i = 0; i < charStamps.size(); i++) {
                charStamps.get(i).stamp(out, lineIndex);
                if (i < charStamps.size() -1) {
                    charStamps.get(i).stampSeparator(out, lineIndex);
                }
            }
            out.println();
        }
    }
}
