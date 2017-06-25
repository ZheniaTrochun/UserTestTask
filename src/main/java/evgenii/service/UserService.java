package evgenii.service;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;

import java.util.List;

/**
 * Created by zhenia on 23.06.17.
 */
public interface UserService {
    List<User> getAllUsers();

    User createUser(UserDTO userDTO) throws UserAlreadyExistsException, InvalidUserFieldsException, NullValueException;

    User getCustomerById(Integer id) throws InvalidUserFieldsException;

    User getCustomerByName(String name) throws InvalidUserFieldsException;
}
