package evgenii.service;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;

/**
 * Created by zhenia on 23.06.17.
 */
public interface AdminService {
    User updateUserFields(UserDTO userDTO)
            throws InvalidUserFieldsException, UserAlreadyExistsException, NullValueException;
}
