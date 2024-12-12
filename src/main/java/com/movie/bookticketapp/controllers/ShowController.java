package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.ShowDao;
import com.movie.bookticketapp.dao.ScreenDao;
import com.movie.bookticketapp.models.Show;
import com.movie.bookticketapp.models.Screen;
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
    public String showAddShowForm(@RequestParam("screenId") int screenId, @ModelAttribute Show show, ModelMap map) {
        Screen screen = screenDao.getScreenById(screenId);
        if (screen == null) {
            return "redirect:/screens/list";
        }
        show.setScreen(screen);
        map.addAttribute("show", show);
        return "add-show";
    }

    @PostMapping("/add")
    public String addShow(@RequestParam("screenId") int screenId, @ModelAttribute Show show) {
        Screen screen = screenDao.getScreenById(screenId);
        if (screen == null) {
            throw new IllegalArgumentException("Screen not found");
        }
        show.setScreen(screen);
        showDao.saveShow(show);
        return "redirect:/theatres/list";
    }

    @GetMapping("/list")
    public String listShows(@RequestParam("screenId") int screenId, ModelMap map) {
        List<Show> shows = showDao.getShowsByScreenId(screenId); // Fetch shows for the given screen
        map.addAttribute("shows", shows);
        return "list-shows";
    }

}
