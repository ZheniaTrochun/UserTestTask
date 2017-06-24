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
import evgenii.utils.ValidationUtils;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationUtils validationUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDTO userDTO)
            throws UserAlreadyExistsException, InvalidUserFieldsException, NullValueException {

        User user = userRepository.save(validationUtils.validateUser(userDTO));

        LOGGER.info("User created: ", user);

        return user;
    }

    @Override
    public User getCustomerById(Integer id) throws InvalidUserFieldsException {
        if ((id < 0) || (id > userRepository.count()))
            throw new InvalidUserFieldsException("Incorrect id");

        return userRepository.findUserById(id);
    }

    @Override
    public User getCustomerByName(String name) throws InvalidUserFieldsException {
        if (validationUtils.validateName(name))
            throw new InvalidUserFieldsException("Name is not valid");

        return userRepository.findUserByUsername(name);
    }
}
