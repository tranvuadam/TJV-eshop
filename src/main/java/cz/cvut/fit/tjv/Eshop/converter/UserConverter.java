package cz.cvut.fit.tjv.Eshop.converter;

import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;

public class UserConverter {
    public static User toModel(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getDateOfBirth());
    }
    public static UserDTO fromModel(User user) {
        return new UserDTO(user.getName(), user.getDateOfBirth());
    }
    public Collection<User> toModelMany(Collection<UserDTO> userDTOs){
        Collection<User> users = new ArrayList<>();
        userDTOs.forEach(p -> users.add(toModel(p)));
        return users;
    }
    public static Collection<UserDTO> fromModelMany(Collection<User> users){
        Collection<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(p -> userDTOs.add(fromModel(p)));
        return userDTOs;
    }
}