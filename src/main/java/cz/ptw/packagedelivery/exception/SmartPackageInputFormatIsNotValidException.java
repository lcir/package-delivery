package cz.ptw.packagedelivery.exception;

/**
 * Exception which is thrown, during validation of input.
 */
public class SmartPackageInputFormatIsNotValidException extends RuntimeException {
    public SmartPackageInputFormatIsNotValidException(String message) {
        super(message);
    }
}
