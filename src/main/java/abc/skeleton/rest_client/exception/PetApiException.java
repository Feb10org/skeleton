package abc.skeleton.rest_client.exception;

public class PetApiException extends RuntimeException {
    public PetApiException(String message) {
        super(message);
    }

    public PetApiException(String message, Throwable t) {
        super(message, t);
    }
}
