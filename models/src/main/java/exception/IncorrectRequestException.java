package exception;

public class IncorrectRequestException extends Exception {
    public IncorrectRequestException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
