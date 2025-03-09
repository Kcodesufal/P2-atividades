package Exceptions;
public class WithdrawLimitExceededException extends RuntimeException {
    public WithdrawLimitExceededException(String message) {
        super(message);
    }
}
