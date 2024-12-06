package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.UserDao;
import com.movie.bookticketapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDao userDao;

    //hello
    @GetMapping("/signup")
    public String helloWorld(
            @ModelAttribute User user,
            ModelMap map
    ) {
        map.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(
            @ModelAttribute User user,
            ModelMap map
    ) {
        List<String> errors = checkUserValidation(user);

        //username check
        if (userDao.isUsernameExists(user.getUserName().trim())) {
            errors.add("An account already exsists with this email. Please use a different email.");
        }

        if (!errors.isEmpty()){
            map.addAttribute("errors", errors);
            map.addAttribute("user", user); // Preserve user input
            return "signup";
        }

        userDao.saveUser(user);
        map.addAttribute("signupMessage", "User signed up successfully!");
        return "login";
    }

    private List<String> checkUserValidation(User user){

        List<String> validationErrors = new ArrayList<>();

        // check for firstname
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()){
            validationErrors.add("firstname is empty, Please Fill!");
        }else if(user.getFirstName().contains(">") || user.getFirstName().contains("<")){
            validationErrors.add("first name should not contain < and > characters ");
        }
        // check for lastname
        if (user.getLastName() != null && user.getLastName().trim().isEmpty()) {
            validationErrors.add("Last name cannot be empty.");
        } else if (user.getLastName() != null && (user.getLastName().contains(">") || user.getLastName().contains("<"))) {
            validationErrors.add("Last name should not contain < and > characters.");
        }
        //check for username(email)
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            validationErrors.add("Username (email) is empty, please fill!");
        }else if (user.getUserName().contains(">") || user.getUserName().contains("<")) {
            validationErrors.add("Username (email) should not contain < and > characters.");
        }
        //check for password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            validationErrors.add("Password is empty, please fill!");
        } else if (user.getPassword().length() < 6) {
            validationErrors.add("Password must be at least 6 characters long.");
        } else if (user.getPassword().contains(">") || user.getPassword().contains("<")) {
            validationErrors.add("Password should not contain < and > characters.");
        }

        return validationErrors;

    }

}

