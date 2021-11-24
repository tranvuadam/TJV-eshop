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
}
