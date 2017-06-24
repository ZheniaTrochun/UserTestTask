package evgenii.service;

import evgenii.dao.UserRepository;
import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by zhenia on 15.06.17.
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);


    @Override
    public User updateUserFields(UserDTO userDTO)
            throws InvalidUserFieldsException, UserAlreadyExistsException, NullValueException {
        if (!Objects.nonNull(userDTO)) {
            throw new InvalidUserFieldsException("All fields are null");
        }

        User appUser = userRepository.findUserByUsername(userDTO.username);

        if (appUser == null) {
            return userService.createUser(userDTO);
        }

        if (userDTO.address != null) appUser.setAddress(userDTO.address);

        if (userDTO.phone != null) appUser.setPhone(userDTO.phone);

        LOGGER.info("User updated: ", appUser);

        return userRepository.save(appUser);
    }
}
