package exception;

import lombok.Getter;

@Getter
public class IncorrectRequestException extends Exception {
    private final String code;

    public IncorrectRequestException(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
