package Exceptions;

public class TooMuchMoneyException extends RuntimeException{
    private String message;

    public TooMuchMoneyException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
