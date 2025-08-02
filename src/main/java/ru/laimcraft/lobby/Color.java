package ru.laimcraft.lobby;

public enum Color {
    RED(0xFF0000),
    GREEN(0x00FF00),
    YELLOW(0xFFFF00),
    WHITE(0xFFFFFF);


    private int color;

    Color(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
