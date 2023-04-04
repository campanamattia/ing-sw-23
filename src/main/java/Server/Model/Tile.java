package Server.Model;

import Enumeration.Color;

public class Tile {

    private Color color;

    public Tile(Color color) {
        this.color = color;
    }

    public Color getTileColor() {
        return color;
    }

}
