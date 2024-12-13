package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.ShowDao;
import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.models.Show;
import com.movie.bookticketapp.models.Screen;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowDao showDao;

    @Autowired
    private ScreenDao screenDao;

    @GetMapping("/add")
    public String showAddShowForm(@RequestParam("screenId") int screenId, @ModelAttribute Show show, ModelMap map,
            HttpSession session
    ) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        Screen screen = screenDao.getScreenById(screenId);
        if (screen == null) {
            return "redirect:/screens/list";
        }
        show.setScreen(screen);
        map.addAttribute("show", show);
        return "add-show";
    }

    @PostMapping("/add")
    public String addShow(@RequestParam("screenId") int screenId, @ModelAttribute Show show, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        Screen screen = screenDao.getScreenById(screenId);
        if (screen == null) {
            throw new IllegalArgumentException("Screen not found");
        }
        show.setScreen(screen);
        showDao.saveShow(show);
        return "redirect:/theatres/list";
    }

    @GetMapping("/list")
    public String listShows(@RequestParam("screenId") int screenId, @RequestParam("theatreId") int theatreId, ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        List<Show> shows = showDao.getShowsByScreenId(screenId); // Fetch shows for the given screen
        map.addAttribute("shows", shows);
        map.addAttribute("screenId", screenId);
        map.addAttribute("theatreId", theatreId);
        return "list-shows";
    }

    @GetMapping("/delete")
    public String deleteShow(@RequestParam("showId") int showId, @RequestParam("screenId") int screenId, @RequestParam("theatreId") int theatreId, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }

        showDao.deleteShowById(showId);
        return "redirect:/shows/list?screenId=" + screenId + "&theatreId=" + theatreId;
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
