package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.SeatDao;
import com.movie.bookticketapp.models.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeatController {

    @Autowired
    SeatDao seatDao;

    @GetMapping("/addSeats")
    public String redirectSeatpage(){
        return "redirect:/seatForm";
    }

    @GetMapping("/seatForm")
    public String viewSeatForm(
            @ModelAttribute Seat seat,
            ModelMap map
    ){
        map.addAttribute("seat", seat);
        return "seatForm";
    }

    @PostMapping("/seatForm")
    public String postSeatForm(
            @ModelAttribute Seat seat,
            ModelMap map
    ){
        seatDao.saveSeat(seat);
        return "redirect:/adminPage";
    }

}
