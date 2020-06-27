package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

import static org.thymeleaf.util.StringUtils.repeat;

public class Vertical implements CharStamp {
    private int width;

    public Vertical(int width) {
        this.width = width;
    }


    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        int startX = (width - 1) / 2;
        out.print(repeat(' ', startX));
        out.print("|");
        out.print(repeat(' ', width - startX - 1));
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {
        out.print(repeat(' ', 3));
    }
}
