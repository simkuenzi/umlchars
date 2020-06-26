package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

import static org.thymeleaf.util.StringUtils.repeat;

public class TopMiddle implements CharStamp {
    private int width;

    public TopMiddle(int width) {
        this.width = width;
    }

    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        switch (lineIndex) {
            case 0:
                out.print(repeat('-', width));
                break;
            case 1:
                out.print(repeat(' ', width));
                break;
        }
    }

    @Override
    public void stampSeparator(PrintWriter out, int lineIndex) {
        switch (lineIndex) {
            case 0:
                out.print(repeat('-', 3));
                break;
            case 1:
                out.print(repeat(' ', 3));
                break;
        }
    }
}
