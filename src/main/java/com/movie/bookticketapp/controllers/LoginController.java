package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.UserDao;
import com.movie.bookticketapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    @GetMapping("/login")
    public String displayLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String userName,
            @RequestParam String password,
            ModelMap map
    ) {

        User user = userDao.findByUserName(userName);

        if (user == null || !user.getPassword().equals(password)) {
            map.addAttribute("error", "Invalid Username or Password");
            return "login";
        }

        map.addAttribute("message", "Login successful!");
        return "homepage";

    }

}
