//package com.movie.bookticketapp.controllers;
//
//
//import com.movie.bookticketapp.dao.ScreenDao;
//import com.movie.bookticketapp.models.Screen;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class ScreenController {
//
//    @Autowired
//    ScreenDao screenDao;
//
//    @GetMapping("/addScreens")
//    public String displayScreenForm(){
//        return "redirect:/screenForm";
//    }
//
//    @GetMapping("/screenForm")
//    public String disForm(
//            @ModelAttribute Screen screen,
//            ModelMap map
//    ){
//        map.addAttribute("screen", screen);
//        return "screenForm";
//    }
//
//
//    @PostMapping("/addScreens")
//    public String addScreenForm(
//            @ModelAttribute Screen screen,
//            ModelMap map
//    ) {
//        screenDao.saveScreen(screen);
//        return "redirect:/adminPage";
//    }
//
//}

package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.models.Screen;
import com.movie.bookticketapp.models.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/screens")
public class ScreenController {

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private TheatreDao theatreDao;


    @GetMapping("/add")
    public String showAddScreenForm(
            @RequestParam("theatreId") int theatreId,
            @ModelAttribute Screen screen,
            ModelMap map) {

        Theatre theatre = theatreDao.getTheatreById(theatreId);


        if (theatre == null) {
            map.addAttribute("errorMessage", "Theatre not found for ID: " + theatreId);
            return "redirect:/theatres/list";
        }


        screen.setTheatre(theatre);


        map.addAttribute("screen", screen);

        return "add-screen";
    }


    @PostMapping("/add")
    public String addScreen(
            @RequestParam("theatreId") int theatreId,
            @ModelAttribute Screen screen) {
        System.out.println("Theatre ID: " + theatreId);
        System.out.println("Screen Number: " + screen.getScreenNumber());

        Theatre theatre = theatreDao.getTheatreById(theatreId);
        if (theatre == null) {
            throw new IllegalArgumentException("Invalid Theatre ID: " + theatreId);
        }

        screen.setTheatre(theatre);
        System.out.println("Associated Theatre: " + screen.getTheatre().getName());

        screenDao.saveScreen(screen);
        return "redirect:/theatres/list";
    }


    @GetMapping("/list")
    public String listScreens(@RequestParam("theatreId") int theatreId, ModelMap map) {
        List<Screen> screens = screenDao.getScreensByTheatreId(theatreId);
        map.addAttribute("screens", screens);
        return "list-screens";
    }
}
