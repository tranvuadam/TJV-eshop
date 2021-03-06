package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.UserJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserJpaRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getById(Long userId){
        return userRepository.getById(userId);
    }

    public boolean exists(Long userId){
        return userRepository.existsById(userId);
    }

    @Transactional
    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    @Transactional
    public User addNewUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public User updateById(Long userId, UserDTO userDTO) {
        User user = userRepository.getById(userId);
        if(userDTO.getName() != null)
            user.setName(userDTO.getName());

        if(userDTO.getDateOfBirth() != null)
            user.setDateOfBirth(userDTO.getDateOfBirth());

        return userRepository.save(user);
    }

}
