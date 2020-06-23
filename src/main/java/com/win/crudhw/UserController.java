package com.win.crudhw;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "adduser";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "adduser";
        }

        userRepository.save(user);
        model.addAttribute("Users", userRepository.findAll());
        return "/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "updateuser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "updateuser";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "/index";
    }

    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    // addtional CRUD methods
}