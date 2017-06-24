package evgenii.exceptions;

/**
 * Created by zhenia on 16.06.17.
 */
public class UserAlreadyExistsException extends Throwable {
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
