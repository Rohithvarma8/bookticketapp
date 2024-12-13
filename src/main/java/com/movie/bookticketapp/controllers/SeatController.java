package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.SeatDao;
import com.movie.bookticketapp.dao.ShowDao;
import com.movie.bookticketapp.models.Seat;
import com.movie.bookticketapp.models.Show;
import jakarta.servlet.http.HttpSession;
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
    public String showAddSeatForm(@RequestParam("showId") int showId, @ModelAttribute Seat seat, ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        Show show = showDao.getShowById(showId);
        if (show == null) {
            return "redirect:/shows/list";
        }
        seat.setShow(show);
        map.addAttribute("seat", seat);
        return "add-seat";
    }

    @PostMapping("/add")
    public String addSeat(@RequestParam("showId") int showId, @ModelAttribute Seat seat, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        Show show = showDao.getShowById(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }
        seat.setShow(show);
        seatDao.saveSeat(seat);
        return "redirect:/theatres/list";
    }

    @GetMapping("/list")
    public String listSeats(
            @RequestParam("showId") int showId,
            @RequestParam("screenId") int screenId,
            @RequestParam("theatreId") int theatreId,
            ModelMap map, HttpSession session
    ) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        Show show = showDao.getShowById(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        List<Seat> seats = seatDao.getSeatsByShowId(showId); // Fetch seats for the given show
        map.addAttribute("seats", seats); // Add seats to the model
        map.addAttribute("showId", showId); // Add show ID for navigation
        map.addAttribute("screenId", screenId); // Add screen ID for navigation
        map.addAttribute("theatreId", theatreId); // Add theatre ID for navigation
        map.addAttribute("show", show); // Add show details to the model for context

        return "list-seats"; // Render the list-seats.html view
    }


    @GetMapping("/delete")
    public String deleteSeat(@RequestParam("seatId") int seatId, @RequestParam("showId") int showId, @RequestParam("screenId") int screenId, @RequestParam("theatreId") int theatreId, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        seatDao.deleteSeatById(seatId);
        return "redirect:/seats/list?showId=" + showId + "&screenId=" + screenId + "&theatreId=" + theatreId;
    }

    @GetMapping("/update")
    public String showUpdateSeatForm(@RequestParam("seatId") int seatId, @RequestParam("showId") int showId, @RequestParam("screenId") int screenId, @RequestParam("theatreId") int theatreId, ModelMap map, HttpSession session) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        Seat seat = seatDao.getSeatById(seatId);
        map.addAttribute("seat", seat);
        map.addAttribute("showId", showId);
        map.addAttribute("screenId", screenId);
        map.addAttribute("theatreId", theatreId);
        return "update-seat"; // Thymeleaf template for updating seat
    }

    @PostMapping("/update")
    public String updateSeat(
            @RequestParam("seatId") int seatId,
            @RequestParam("seatNumber") String seatNumber,
            @RequestParam("booked") boolean booked,
            @RequestParam("showId") int showId,
            @RequestParam("screenId") int screenId,
            @RequestParam("theatreId") int theatreId,
            HttpSession session
    ) {

        String validationResult = validateAdminSession(session);
        if (validationResult != null) {
            return validationResult; // Redirect to appropriate error page if validation fails
        }


        Seat seat = seatDao.getSeatById(seatId);
        if (seat == null) {
            throw new IllegalArgumentException("Seat not found");
        }

        Show show = showDao.getShowById(showId); // Fetch the Show
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        seat.setSeatNumber(seatNumber);
        seat.setBooked(booked);
        seat.setShow(show); // Set the associated Show

        seatDao.updateSeat(seat);

        // Redirect back to the seat list
        return "redirect:/seats/list?showId=" + showId + "&screenId=" + screenId + "&theatreId=" + theatreId;
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
