//package com.movie.bookticketapp.controllers;
//
//
//import com.movie.bookticketapp.dao.TheatreDao;
//import com.movie.bookticketapp.enums.Location;
//import com.movie.bookticketapp.models.Theatre;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class TheatreController {
//
//    @Autowired
//    TheatreDao theatreDao;
//
//    @GetMapping("/addTheatre")
//    public String redirectToFormUrl(){
//        return "redirect:/theatreForm";
//    }
//
//    @GetMapping("/theatreForm")
//    public String viewTheatreForm(
//            @ModelAttribute Theatre theatre,
//            ModelMap map
//    ){
//        map.addAttribute("locations", Location.values());
//        map.addAttribute("theatre", theatre);
//        return "theatreForm";
//    }
//
//    @PostMapping("/addTheatre")
//    public String handleTheatre(
//            @ModelAttribute Theatre theatre,
//            ModelMap map
//    ){
//        theatreDao.saveTheatre(theatre);
//        return "redirect:/adminPage";
//    }
//
//
//}
package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.enums.Location;
import com.movie.bookticketapp.models.Theatre;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    private TheatreDao theatreDao;


    @GetMapping("/add")
    public String showAddTheatreForm(@ModelAttribute Theatre theatre, ModelMap map, HttpSession session) {
        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }
        map.addAttribute("theatre", theatre);
        map.addAttribute("locations", Location.values());
        return "add-theatre";
    }

    @PostMapping("/add")
    public String addTheatre(@ModelAttribute("theatre") Theatre theatre, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        theatreDao.saveTheatre(theatre);
        return "redirect:/theatres/list";
    }

    @GetMapping("/list")
    public String listTheatres(ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        List<Theatre> theatres = theatreDao.getAllTheatres();
        map.addAttribute("theatres", theatres);
        return "list-theatres";
    }

    @GetMapping("/delete")
    public String deleteTheatre(@RequestParam("theatreId") int theatreId, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        theatreDao.deleteTheatreById(theatreId);
        return "redirect:/theatres/list";
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

