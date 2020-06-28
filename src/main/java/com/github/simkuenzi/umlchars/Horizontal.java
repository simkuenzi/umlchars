package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

import static org.thymeleaf.util.StringUtils.repeat;

public class Horizontal implements CharStamp {
    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        out.write(repeat(lineIndex == 1 ? '-' : ' ', 3));
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {

    }
}
