package com.movie.bookticketapp.controllers;


import com.movie.bookticketapp.dao.SeatDao;
import com.movie.bookticketapp.models.Seat;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("u/payment")
public class PaymentController {

    @Autowired
    SeatDao seatDao;
    @Autowired
    private ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;

    @GetMapping("/details")
    public String getPayment(
            @RequestParam int movieId,
            @RequestParam int theatreId,
            @RequestParam int screenId,
            @RequestParam int seatId,
            ModelMap map
    ){

        map.addAttribute("movieId", movieId);
        map.addAttribute("theatreId", theatreId);
        map.addAttribute("screenId", screenId);
        map.addAttribute("seatId", seatId);

        return "card-details";

    }

    @PostMapping("/submit")
    public String submitPayment(
            @RequestParam int movieId,
            @RequestParam int theatreId,
            @RequestParam int screenId,
            @RequestParam int seatId,
            ModelMap map, HttpSession session
    ){


        String ses = validateAdminSession(session);

        if(ses != null){

            return ses;

        }

        Seat seat = seatDao.getSeatById(seatId);

        seat.setBooked(true);

        seatDao.updateSeat(seat);

        map.addAttribute("movieId", movieId);
        map.addAttribute("theatreId", theatreId);
        map.addAttribute("screenId", screenId);
        map.addAttribute("seatId", seatId);

        return "redirect:/u/homepage";
    }

    private String validateAdminSession(HttpSession session) {
        if (session == null) {
            return "no-session"; // Redirect to no-session page
        }

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null || !role.equalsIgnoreCase("user")) {
            session.invalidate(); // Invalidate session if invalid or unauthorized
            return "redirect:/login"; // Redirect to unauthorized page
        }

        return null; // Session is valid
    }

}
