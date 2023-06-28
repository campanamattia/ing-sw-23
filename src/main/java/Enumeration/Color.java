package Enumeration;

/**
 * An enumeration representing colors of the tiles.
 */
public enum Color {
    GREEN("GREEN", "\u001b[42;1m"),
    WHITE("WHITE", "\u001b[47;1m"),
    YELLOW("YELLOW", "\u001b[43;1m"),
    BLUE("BLUE", "\u001b[44;1m"),
    CYAN("CYAN", "\u001b[46;1m"),
    PINK("PINK", "\u001b[45;1m");

    private final String code;
    private final String color;

    /**
     * Constructs each color.
     * @param code         the code of the color.
     */
    Color(String color, String code) {
        this.color = color;
        this.code = code;
    }

    /**
     * Returns the ANSI escape code of the color.
     * @return the ANSI escape code of the color.
     */
    public String getCode(){
        return this.code;
    }

    /**
     * Returns the name of the colors.
     *
     * @return the code of the color.
     */
    @Override
    public String toString() {
        return color;
    }
}
