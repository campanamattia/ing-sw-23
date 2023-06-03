package Client.View.Cli;

public enum CliColor {

    //Color string, color reset
    RESET("\033[0m"),
    RESET_LINE("\033[2K\r"),
    RESET_DOWN("\r\033[0J"),
    CLEAR_ALL("\033[H\033[2J"),

    //Regular Colors
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    PINK("\033[0;35m"),
    CYAN("\033[0;36m"),
    WHITE("\033[0;37m"),


    //Bold Colors
    BOLD("\033[1m"),
    BOLDBLACK("\033[1;30m"),
    BOLDRED("\033[1;31m"),
    BOLDGREEN("\033[1;32m"),
    BOLDYELLOW("\033[1;33m"),
    BOLDBLUE("\033[1;34m"),
    BOLDPINK("\033[1;35m"),
    BOLDCYAN("\033[1;36m"),
    BOLDWHITE("\033[1;37m"),

    //Background Colors
    BBLACK("\u001b[40m"),
    BRED("\033[1;37m\033[41m"),
    BGREEN("\033[1;37m\033[42m"),
    BYELLOW("\033[0;30m\033[103m"),
    BBLUE("\033[1;37m\033[44m"),
    BPINK("\033[1;37m\033[105m"),
    BCYAN("\033[0;30m\033[46m"),
    BWHITE("\033[0;30m\033[47m"),
    BGREY("\033[1;37m\033[100m");


    private final String code;

    /**
     * Constructs each color.
     *
     * @param code         the code of the color.
     */
    CliColor(String code) {
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
