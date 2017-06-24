package evgenii.controller;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;
import evgenii.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @ApiOperation(httpMethod = "POST",
            value = "Resource to update user fields",
            response = User.class,
            produces = "application/json")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public User updateUserFields(@RequestBody UserDTO userDto)
            throws InvalidUserFieldsException, UserAlreadyExistsException, NullValueException {

        return adminService.updateUserFields(userDto);
    }
}
