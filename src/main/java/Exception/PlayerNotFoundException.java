package Exception;

public class PlayerNotFoundException extends Exception{
    public PlayerNotFoundException(String id) {
        super(id+" doesn't exist!");
    }
}
