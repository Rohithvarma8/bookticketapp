package com.movie.bookticketapp.controllers;


import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.enums.Location;
import com.movie.bookticketapp.models.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TheatreController {

    @Autowired
    TheatreDao theatreDao;

    @GetMapping("/addTheatre")
    public String redirectToFormUrl(){
        return "redirect:/theatreForm";
    }

    @GetMapping("/theatreForm")
    public String viewTheatreForm(
            @ModelAttribute Theatre theatre,
            ModelMap map
    ){
        map.addAttribute("locations", Location.values());
        map.addAttribute("theatre", theatre);
        return "theatreForm";
    }

    @PostMapping("/addTheatre")
    public String handleTheatre(
            @ModelAttribute Theatre theatre,
            ModelMap map
    ){
        theatreDao.saveTheatre(theatre);
        return "redirect:/adminPage";
    }


}
