package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.UserService;
import cz.cvut.fit.tjv.Eshop.converter.UserConverter;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * To use user service, start the server and send requests to localhost:8080/user
 */
@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
    * Send get request to localhost:8080/user/ to view all users
    */
    @GetMapping(path = "/")
    public Collection<UserDTO> getUsers(){
        return UserConverter.fromModelMany(userService.getUsers());
    }
    /**
     * Send get request to localhost:8080/user/ to view a user by ID
     */
    @GetMapping("/{userId}")
    public Object getById(@PathVariable("userId") Long userId){
        UserDTO user;
        try {
            user = UserConverter.fromModel(userService.getById(userId));
        }catch (IllegalArgumentException e){
            return HttpStatus.NOT_FOUND;
        }
        return user;
    }

    /**
     * Send post request with a request body to localhost:8080/user/ to create a new user
     * Example:
     *      POST localhost:8080/user/
     *      {
     *          "name": "userExample"
     *      }
     */
    @PostMapping("/")
    public HttpStatus registerNewProduct(@RequestBody UserDTO userDTO){
        try {
            userService.addNewUser(UserConverter.toModel(userDTO));
        } catch (IllegalArgumentException e){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

}
