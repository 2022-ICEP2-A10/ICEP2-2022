package exceptions;


public class MemberException extends IllegalArgumentException {
    public MemberException() {
    }

    public MemberException(String s) {
        super(s);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(Throwable cause) {
        super(cause);
    }
}