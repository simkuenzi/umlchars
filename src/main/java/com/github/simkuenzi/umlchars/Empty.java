package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

import static org.thymeleaf.util.StringUtils.repeat;

public class Empty implements CharStamp {
    private int width;

    public Empty(int width) {
        this.width = width;
    }

    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        out.print(repeat(' ', width));
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {
        out.print(repeat(' ', 3));
    }
}
