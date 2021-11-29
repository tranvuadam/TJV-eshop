package cz.cvut.fit.tjv.Eshop.business;

import cz.cvut.fit.tjv.Eshop.dao.SalesPackageJpaRepository;
import cz.cvut.fit.tjv.Eshop.dao.UserJpaRepository;
import cz.cvut.fit.tjv.Eshop.domain.Product;
import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.ProductDTO;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @Test
    void updateByIdNoOverwrite() {
        User user = new User("user1", LocalDate.now());

        UserDTO userDTO = new UserDTO(null, null, 1L);

        Mockito.when(userService.getById(1L)).thenReturn(user);
        Mockito.when(userJpaRepository.save(user)).thenReturn(user);
        User userReturned = userService.updateById(1L, userDTO);
        Assertions.assertEquals(user, userReturned);
    }

    @Test
    void updateByIdOverwrite() {
        User user = new User("user1", LocalDate.now());

        UserDTO userDTO = new UserDTO("user2", LocalDate.now().minusDays(2), 1L);

        Mockito.when(userService.getById(1L)).thenReturn(user);
        Mockito.when(userJpaRepository.save(user)).thenReturn(user);
        User userReturned = userService.updateById(1L, userDTO);
        Assertions.assertEquals("user2", userReturned.getName());
        Assertions.assertEquals(LocalDate.now().minusDays(2), userReturned.getDateOfBirth());

    }
}