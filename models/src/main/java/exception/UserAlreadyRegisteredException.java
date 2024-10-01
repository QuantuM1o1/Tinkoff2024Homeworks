package exception;

import lombok.Getter;

@Getter
public class UserAlreadyRegisteredException extends Exception {
    private final String code;

    public UserAlreadyRegisteredException(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
