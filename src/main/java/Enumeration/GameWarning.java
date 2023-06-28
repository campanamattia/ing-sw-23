package Enumeration;

public enum GameWarning {
    START_TIMER("You're the only player left, start now a 60 seconds timer!"),
    STOP_TIMER ("Some players rejoined, the game continues!"),
    WON("You won due to insufficient players!"),
    LAST_ROUND("The game will end after the last few turns!");

    private final String ms;

    GameWarning(String s) {
        this.ms = s;
    }

    public String getMs(){
        return this.ms;
    }
}
