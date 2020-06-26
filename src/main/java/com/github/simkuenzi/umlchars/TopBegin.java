package com.github.simkuenzi.umlchars;

import java.io.PrintWriter;

import static org.thymeleaf.util.StringUtils.repeat;

public class TopBegin implements CharStamp {
    private int width;

    public TopBegin(int width) {
        this.width = width;
    }

    @Override
    public void stamp(PrintWriter out, int lineIndex) {
        int startX = (width - 1) / 2;

        switch (lineIndex) {
            case 0:
                out.print(repeat(' ', startX));
                out.print("+");
                out.print(repeat('-', width - startX - 1));
                break;

            case 1:
                out.print(repeat(' ', startX));
                out.print("|");
                out.print(repeat(' ', width - startX - 1));
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
