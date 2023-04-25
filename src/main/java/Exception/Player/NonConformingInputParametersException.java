package Exception.Player;
import Exception.PlayerException;

public class NonConformingInputParametersException extends PlayerException {
    public NonConformingInputParametersException() {
        super("Non-conforming input parameters");
    }

    public NonConformingInputParametersException(String string) {
        super(string);
    }
}
