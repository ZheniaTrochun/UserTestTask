package evgenii.controller;

import evgenii.exceptions.InvalidUserFieldsException;
import evgenii.exceptions.NullValueException;
import evgenii.exceptions.UserAlreadyExistsException;
import evgenii.model.User;
import evgenii.model.dto.UserDTO;
import evgenii.service.AdminService;
import evgenii.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import evgenii.service.AdminServiceImpl;
import evgenii.service.UserServiceImpl;

import java.util.Iterator;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get all users",
            response = Iterable.class,
            produces = "application/json")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @ApiOperation(httpMethod = "POST",
            value = "Resource to create new user",
            response = User.class,
            produces = "application/json")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public User createNewUser(@RequestBody UserDTO userDTO)
            throws InvalidUserFieldsException, UserAlreadyExistsException, NullValueException {
        System.out.println("hello");

        return userService.createUser(userDTO);
    }


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get customer by id",
            response = User.class,
            produces = "application/json")
    @RequestMapping(value = "/getCustomerById", method = RequestMethod.GET)
    public User getCustomerById(@RequestParam Integer id)
            throws InvalidUserFieldsException {

        return userService.getCustomerById(id);
    }


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get customer by username",
            response = User.class,
            produces = "application/json")
    @RequestMapping(value = "/getCustomerByName", method = RequestMethod.GET)
    public User getCustomerByName(@RequestParam String name)
            throws InvalidUserFieldsException {

        return userService.getCustomerByName(name);
    }
}
