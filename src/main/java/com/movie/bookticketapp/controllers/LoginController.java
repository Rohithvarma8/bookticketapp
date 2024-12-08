package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.RoleDao;
import com.movie.bookticketapp.dao.UserDao;
import com.movie.bookticketapp.models.Role;
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

    @Autowired
    RoleDao roleDao;


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

        Role role = user.getRole();

        if(role.getId() == 2){
            map.addAttribute("message", "Login successful!");
            return "adminPage";
        } else if (role.getId() == 1) {
            return "homepage";
        }

        map.addAttribute("error","Role not found");
        return "login";

    }

}
