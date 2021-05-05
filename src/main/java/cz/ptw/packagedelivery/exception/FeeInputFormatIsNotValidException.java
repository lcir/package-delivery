package cz.ptw.packagedelivery.exception;

/**
 * Exception which is thrown, during validation of fee input.
 */
public class FeeInputFormatIsNotValidException extends RuntimeException {
    public FeeInputFormatIsNotValidException(String message) {
        super(message);
    }
}
