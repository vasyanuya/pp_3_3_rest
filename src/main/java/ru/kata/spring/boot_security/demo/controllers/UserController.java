package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
//@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String homePage() {
        return "index";
    }
    @GetMapping(value = "/admin")
    public String listUsers(Model model) {
        model.addAttribute("listUsers", userService.listUsers());
        return "admin";
    }

    @GetMapping("/user")
    public String readUser(Principal principal, Model model) {
        model.addAttribute("userPrincipal", principal);
        return "user";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") Long id, Model model ) {
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }
    @PostMapping("/edit")
    public String updateEditUser (@ModelAttribute("user") @Valid User user,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "edit";
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
