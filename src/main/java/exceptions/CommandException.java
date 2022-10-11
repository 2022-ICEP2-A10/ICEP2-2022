package exceptions;

public class CommandException extends IllegalArgumentException {
    public CommandException() {
    }

    public CommandException(String s) {
        super(s);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
