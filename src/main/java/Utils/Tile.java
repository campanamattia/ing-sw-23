package Utils;

import Enumeration.Color;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Tile implements Cloneable, Serializable {
    private Color color;

    public Tile(Color color) {
        this.color = color;
    }

    public Color getTileColor() {
        return color;
    }

    public Color getColor(){
        return color;
    }
}
