package Enumeration;

public enum Color {
    GREEN("\u001b[42;1m"),
    WHITE("\u001b[47;1m"),
    YELLOW("\u001b[43;1m"),
    BLUE("\u001b[44;1m"),
    CYAN("\u001b[46;1m"),
    PINK("\u001b[45;1m");

    private final String code;

    /**
     * Constructs each color.
     *
     * @param code         the code of the color.
     */
    Color(String code) {
        this.code = code;
    }

    /**
     * Returns the code of the color.
     *
     * @return              the code of the color.
     */
    @Override
    public String toString() {
        return code;
    }
}
