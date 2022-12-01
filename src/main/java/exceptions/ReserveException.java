package exceptions;

public class ReserveException extends IllegalArgumentException {
    public ReserveException() {
    }

    public ReserveException(String s) {
        super(s);
    }

    public ReserveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReserveException(Throwable cause) {
        super(cause);
    }
}
