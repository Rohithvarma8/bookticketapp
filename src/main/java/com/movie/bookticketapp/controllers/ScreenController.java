package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.models.Screen;
import com.movie.bookticketapp.models.Theatre;
import jakarta.servlet.http.HttpSession;
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
            ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


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
            @ModelAttribute Screen screen, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        System.out.println("Theatre ID: " + theatreId);
        System.out.println("Screen Number: " + screen.getScreenNumber());

        Theatre theatre = theatreDao.getTheatreById(theatreId);
        if (theatre == null) {
            throw new IllegalArgumentException("Invalid Theatre ID: " + theatreId);
        }

        screen.setTheatre(theatre);
        System.out.println("Associated Theatre: " + screen.getTheatre().getName());

        screenDao.saveScreen(screen);
        return "redirect:/screens/list?theatreId=" + theatreId;
    }


    @GetMapping("/list")
    public String listScreens(@RequestParam("theatreId") int theatreId, ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        List<Screen> screens = screenDao.getScreensByTheatreId(theatreId);
        map.addAttribute("screens", screens);
        map.addAttribute("theatreId", theatreId);
        return "list-screens";
    }

    @GetMapping("/delete")
    public String deleteScreen(@RequestParam("screenId") int screenId, @RequestParam("theatreId") int theatreId, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        screenDao.deleteScreenById(screenId);
        return "redirect:/screens/list?theatreId=" + theatreId;
    }

    private String validateAdminSession(HttpSession session) {
        if (session == null) {
            return "no-session"; // Redirect to no-session page
        }

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null || !role.equalsIgnoreCase("ADMIN")) {
            session.invalidate(); // Invalidate session if invalid or unauthorized
            return "unauthorised"; // Redirect to unauthorized page
        }

        return null; // Session is valid
    }
}
