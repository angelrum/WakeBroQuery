package system.service.exception;

/**
 * Created by vladimir on 19.03.2018.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
