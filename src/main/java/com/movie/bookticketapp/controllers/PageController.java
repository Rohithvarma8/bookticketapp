package com.movie.bookticketapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/homepage")
    public String homepage() {
        return "homepage"; // This should match the name of the HTML template
    }

}

