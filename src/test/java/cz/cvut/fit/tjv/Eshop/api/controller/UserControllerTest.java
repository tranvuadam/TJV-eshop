package cz.cvut.fit.tjv.Eshop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.cvut.fit.tjv.Eshop.business.UserService;
import cz.cvut.fit.tjv.Eshop.domain.User;
import cz.cvut.fit.tjv.Eshop.dto.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User user1 = new User("user1", LocalDate.now());
    User user2 = new User("user2", LocalDate.now());
    User user3 = new User("user3", LocalDate.now());
    User user4 = new User("user4", LocalDate.now());
    List<User> users = List.of(user1, user2, user3, user4);
    String testDateOfBirth = "23.12.1991";
    UserDTO userDTO = new UserDTO("user1", LocalDate.of(1991, 12, 23), 1L);

    private String UserToJson(UserDTO userDTO){
        return "{" + "\"id\": " + '"' + userDTO.getId().toString() + '"' + ", "
                + "\"name\": " + '"' + userDTO.getName() + '"' + ", "
                + "\"dateOfBirth\": " + '"' + testDateOfBirth + '"'  + "}";
    }

    @BeforeEach
    void setup(){
        Mockito.when(userService.getUsers()).thenReturn(users);
        Mockito.when(userService.getById(1L)).thenReturn(user1);
        Mockito.when(userService.exists(1L)).thenReturn(true);
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("user1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("user2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("user3")));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("user1")));
    }

    @Test
    void updateById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(userDTO);

        Mockito.when(userService.updateById(1L, userDTO )).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/1").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("user1")));
    }

    @Test
    void deleteByIdFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void registerNewUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(userDTO);

        Mockito.when(userService.addNewUser( user1 )).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.post("/user").accept("application/json").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("user1")));
    }
}