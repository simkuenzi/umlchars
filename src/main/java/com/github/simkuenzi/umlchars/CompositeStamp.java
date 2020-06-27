package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CompositeStamp implements CharStamp {

    private List<CharStamp> charStamps;

    public CompositeStamp(List<CharStamp> charStamps) {
        this.charStamps = charStamps;
    }

    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        stamp(out, (s, o) -> s.stamp(o, lineIndex));
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {
        stamp(out, (s, o) -> s.stampSeparator(o, lineIndex));

    }

    private void stamp(PrintWriter out, StampMethod method) {
        List<Character> chars = new ArrayList<>();

        for (CharStamp stamp : charStamps) {
            StringWriter writer = new StringWriter();
            PrintWriter stampOut = new PrintWriter(writer);
            method.stamp(stamp, stampOut);
            stampOut.flush();

            for (int i = 0; i < writer.toString().length(); i++) {
                char c = writer.toString().charAt(i);
                if (i >= chars.size()) {
                    chars.add(c);
                } else if (!Character.isWhitespace(c)) {
                    chars.set(i, c);
                }
            }
        }

        chars.forEach(out::print);
    }

    private interface StampMethod {
        void stamp(CharStamp charStamp, PrintWriter out);
    }
}
