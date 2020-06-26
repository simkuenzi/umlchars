package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

public interface CharStamp {
    void stamp(PrintWriter out, int lineIndex);
    void stampSeparator(PrintWriter out, int lineIndex);
}
