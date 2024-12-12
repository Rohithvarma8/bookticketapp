package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.SeatDao;
import com.movie.bookticketapp.dao.ShowDao;
import com.movie.bookticketapp.models.Seat;
import com.movie.bookticketapp.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private ShowDao showDao;

    @GetMapping("/add")
    public String showAddSeatForm(@RequestParam("showId") int showId, @ModelAttribute Seat seat, ModelMap map) {
        Show show = showDao.getShowById(showId);
        if (show == null) {
            return "redirect:/shows/list";
        }
        seat.setShow(show);
        map.addAttribute("seat", seat);
        return "add-seat";
    }

    @PostMapping("/add")
    public String addSeat(@RequestParam("showId") int showId, @ModelAttribute Seat seat) {
        Show show = showDao.getShowById(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }
        seat.setShow(show);
        seatDao.saveSeat(seat);
        return "redirect:/theatres/list";
    }

    @GetMapping("/list")
    public String listSeats(@RequestParam("showId") int showId, ModelMap map) {
        Show show = showDao.getShowById(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        List<Seat> seats = seatDao.getSeatsByShowId(showId); // Fetch seats for the given show
        map.addAttribute("seats", seats); // Add seats to the model
        map.addAttribute("show", show); // Add show details to the model for context
        return "list-seats"; // Render the list-seats.html view
    }

}
