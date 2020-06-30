package com.github.simkuenzi.umlchars;

public class Shape {
    private final int x;
    private final int y;
    private final String[] rows;

    public Shape(int x, int y, String... rows) {
        this.x = x;
        this.y = y;
        this.rows = rows;
    }

    public char charAt(int x, int y) {
        int localX = x - this.x;
        int localY = y - this.y;
        return localY >= 0 && localY < rows.length && localX >= 0 && localX < rows[localY].length() ? rows[localY].charAt(localX) : ' ';
    }
}
