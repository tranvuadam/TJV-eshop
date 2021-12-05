package cz.cvut.fit.tjv.social_network.web.ui;

import cz.cvut.fit.tjv.social_network.web.data.UserClient;
import cz.cvut.fit.tjv.social_network.web.model.UserDTO;
import cz.cvut.fit.tjv.social_network.web.model.UserWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
@RequestMapping("/user")
public class UserWebController {
    private final UserClient userClient;

    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userClient.readAll());
        return "users";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("userDto", userClient.readById(Id));
        return "userEdit";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long Id, Model model) {
        model.addAttribute("userDto", userClient.delete(Id));
        return "redirect:/user";
    }

    @PostMapping("/edit")
    public String editUserSubmit(@ModelAttribute UserDTO userDto, Model model) {
        model.addAttribute("userDto", userClient.update(userDto));
        return "userEdit";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("userWebModel", new UserWebModel());
        return "userAdd";
    }

    @PostMapping("/add")
    public String addUserSubmit(@ModelAttribute UserWebModel userWebModel, Model model) {
        model.addAttribute("userWebModel",
                userClient.create(userWebModel).onErrorResume(WebClientResponseException.Conflict.class, e -> Mono.just(new UserWebModel(true, userWebModel))));
        return "userAdd";
    }
}
