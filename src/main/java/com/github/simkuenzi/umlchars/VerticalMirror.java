package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

public class VerticalMirror implements CharStamp {
    private CharStamp inner;

    public VerticalMirror(CharStamp inner) {
        this.inner = inner;
    }


    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        inner.stamp(out, 1 - lineIndex);
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {
        inner.stampSeparator(out, 1 - lineIndex);
    }
}
