package exceptions;

public class ArgumentException extends IllegalArgumentException {
    public ArgumentException() {
    }

    public ArgumentException(String s) {
        super(s);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }
}
