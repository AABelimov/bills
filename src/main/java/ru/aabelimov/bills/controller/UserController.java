package ru.aabelimov.bills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aabelimov.bills.dto.CreateOrUpdateUserDto;
import ru.aabelimov.bills.entity.User;
import ru.aabelimov.bills.service.UserService;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        User user = userService.createUser(createOrUpdateUserDto);
        return "redirect:/orders/user/%d".formatted(user.getId());
    }

    @GetMapping
    public String getUsers(@RequestParam(required = false) String nameOrNumberPhone, Model model) {
        List<User> users = nameOrNumberPhone == null ? userService.getUsers() : userService.findByNameOrNumberPhone(nameOrNumberPhone);
        model.addAttribute("users", users);
        return "user/users";
    }

    @PatchMapping("{id}/edit")
    public String updateUser(@PathVariable Long id, CreateOrUpdateUserDto createOrUpdateUserDto) {
        userService.updateUser(id, createOrUpdateUserDto);
        return null;
    }

    @DeleteMapping("{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "redirect:/users";
    }
}
