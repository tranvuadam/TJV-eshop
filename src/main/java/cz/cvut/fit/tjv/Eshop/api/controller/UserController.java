package cz.cvut.fit.tjv.Eshop.api.controller;

import cz.cvut.fit.tjv.Eshop.business.UserService;
import cz.cvut.fit.tjv.Eshop.converter.ProductConverter;
import cz.cvut.fit.tjv.Eshop.converter.SalesPackageConverter;
import cz.cvut.fit.tjv.Eshop.converter.UserConverter;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import cz.cvut.fit.tjv.Eshop.dto.SalesPackageDTO;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "")
    public Collection<UserDTO> getUsers(){
        return UserConverter.fromModelMany(userService.getUsers());
    }

    @GetMapping("/{userId}")
    public UserDTO getById(@PathVariable("userId") Long userId){
        UserDTO user;
        try {
            user = UserConverter.fromModel(userService.getById(userId));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        return user;
    }

    @PostMapping("/{userId}")
    public Object updateById(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO){
        if (userService.exists(userId)){
            return UserConverter.fromModel(userService.updateById(userId, userDTO));
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @DeleteMapping("/{userId}")
    public Object deleteById(@PathVariable("userId") Long userId){
        if (userService.exists(userId)){
            userService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new UserDTO());
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
    }

    @PostMapping("")
    public UserDTO registerNewUser(@RequestBody UserDTO userDTO){
        UserDTO newUserDTO;
        try {
            newUserDTO = UserConverter.fromModel(userService.addNewUser(UserConverter.toModel(userDTO)));
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return newUserDTO;
    }

}
