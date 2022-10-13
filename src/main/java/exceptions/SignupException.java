package exceptions;

public class SignupException extends IllegalArgumentException {
    public SignupException() {
    }

    public SignupException(String s) {
        super(s);
    }

    public SignupException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignupException(Throwable cause) {
        super(cause);
    }
}
