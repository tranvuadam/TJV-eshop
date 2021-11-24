package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.UserService;
import cz.cvut.fit.tjv.Eshop.converter.ProductConverter;
import cz.cvut.fit.tjv.Eshop.converter.UserConverter;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
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
    public Collection<User> getUsers(){
        return userService.getUsers();
    }
    /**
     * Send get request to localhost:8080/user/ to view a user by ID
     */
    @GetMapping("/{userId}")
    public User getById(@PathVariable("userId") Long userId){
        User user;
        try {
            user = userService.getById(userId);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        return user;
    }

    @DeleteMapping("/{userId}")
    public Object deleteById(@PathVariable("userId") Long userId){
        if (userService.exists(userId)){
            userService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted user with ID: " + userId);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
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
    public User registerNewUser(@RequestBody UserDTO userDTO){
        User user;
        try {
            user = userService.addNewUser(UserConverter.toModel(userDTO));
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return user;
    }

}
