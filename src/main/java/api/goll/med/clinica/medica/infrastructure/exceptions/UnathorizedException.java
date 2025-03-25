package api.goll.med.clinica.medica.infrastructure.exceptions;

public class UnathorizedException extends RuntimeException {

    public UnathorizedException(String message) {
        super(message);

    }

    public UnathorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
