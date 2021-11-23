package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.UserJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserJpaRepository userRepository;
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getById(Long userId){
        User user;
        try {
            user = userRepository.getById(userId);
        }
        catch (EntityNotFoundException e){
            throw new IllegalStateException("User with Id " + userId + " does not exist.");
        }
        return user;
    }
    @Transactional
    public void addNewUser(User user){
        userRepository.save(user);
    }
}
