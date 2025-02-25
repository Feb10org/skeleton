package abc.skeleton.cucumber.user;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private final HttpStatus statusCode;

    public UserException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
