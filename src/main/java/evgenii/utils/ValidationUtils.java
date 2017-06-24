package evgenii.utils;

import evgenii.dao.UserRepository;
import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by zhenia on 15.06.17.
 */

@Component
public class ValidationUtils {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_-]{3,15}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", Pattern.CASE_INSENSITIVE);
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("[\\w',-\\\\.\\s]", Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserRepository userRepository;

    public User validateUser(UserDTO userDTO) throws UserAlreadyExistsException, InvalidUserFieldsException, NullValueException {
        if (Objects.isNull(userDTO))
            throw new NullValueException("DTO is not present");

        if (userRepository.findUserByUsername(userDTO.username) != null)
            throw new UserAlreadyExistsException("User with this username exists!");

//        if (!validateName(userDTO.username) || !validatePhone(userDTO.phone) || !validateAddress(userDTO.address))
//            throw new InvalidUserFieldsException("User fields validation failed");

        return new User(userDTO);
    }

    public boolean validateName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public boolean validatePhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean validateAddress(String address) {
        return address != null && ADDRESS_PATTERN.matcher(address).matches();
    }
}
