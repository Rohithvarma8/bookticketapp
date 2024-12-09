package com.movie.bookticketapp.controllers;


import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.models.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScreenController {

    @Autowired
    ScreenDao screenDao;

    @GetMapping("/addScreens")
    public String displayScreenForm(){
        return "redirect:/screenForm";
    }

    @GetMapping("/screenForm")
    public String disForm(
            @ModelAttribute Screen screen,
            ModelMap map
    ){
        map.addAttribute("screen", screen);
        return "screenForm";
    }


    @PostMapping("/addScreens")
    public String addScreenForm(
            @ModelAttribute Screen screen,
            ModelMap map
    ) {
        screenDao.saveScreen(screen);
        return "redirect:/adminPage";
    }

}
