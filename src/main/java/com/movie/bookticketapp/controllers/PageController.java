package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.*;
import com.movie.bookticketapp.models.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/u")
public class PageController {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieDetailsDao movieDetailsDao;

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private SeatDao seatDao;

    @GetMapping("/homepage")
    public String homepage(
            @RequestParam(defaultValue = "0") int page, // Current page (0-based index)
            @RequestParam(defaultValue = "10") int limit, // Movies per page
            ModelMap map) {

        int offset = page * limit; // Calculate offset
        List<Movie> movies = movieDao.findMoviesWithPagination(limit, offset); // Fetch movies with pagination
        long totalMovies = movieDao.countMovies(); // Total number of movies
        int totalPages = (int) Math.ceil((double) totalMovies / limit); // Calculate total pages

        map.addAttribute("movies", movies);
        map.addAttribute("currentPage", page);
        map.addAttribute("totalPages", totalPages);

        return "homepage"; // Thymeleaf template
    }

    @GetMapping("/movieDetails")
    public String getMovieDetails(@RequestParam("movieId") int movieId, ModelMap map) {
        MovieDetails movieDetails = movieDetailsDao.getMovieDetailsByMovieId(movieId);
        if (movieDetails == null) {
            throw new IllegalArgumentException("No MovieDetails found for Movie ID: " + movieId);
        }

        map.addAttribute("movieDetails", movieDetails);
        return "movie-details"; // Name of the updated Thymeleaf template
    }

    @GetMapping("/screens")
    public String getScreensForTheatre(
            @RequestParam("movieId") int movieId,
            @RequestParam("theatreId") int theatreId,
            ModelMap map) {
        // Fetch movie details

        List<Screen> screens = screenDao.getScreensByTheatreId(theatreId) ;

        if(screens.size() == 0){
            return "no-shows-available";
        }

        // Add data to the model
        map.addAttribute("movieId", movieId);
        map.addAttribute("theatreId", theatreId);
        map.addAttribute("screens", screens);

        return "screens-list"; // Name of the Thymeleaf template
    }

    @GetMapping("/shows")
    public String getShowsForScreen(
            @RequestParam("movieId") int movieId,
            @RequestParam("theatreId") int theatreId,
            @RequestParam("screenId") int screenId,
            ModelMap map
    ){

        List<Show> shows = showDao.getShowsByScreenId(screenId);

        if(shows.size() == 0){
            return "no-shows-available";
        }

        map.addAttribute("movieId", movieId);
        map.addAttribute("theatreId", theatreId);
        map.addAttribute("screenId",screenId);
        map.addAttribute("shows", shows);

        return "shows-list";
    }

    @GetMapping("/seats")
    public String getSeatFromScreen(
            @RequestParam("movieId") int movieId,
            @RequestParam("theatreId") int theatreId,
            @RequestParam("screenId") int screenId,
            @RequestParam("showId") int showId,
            ModelMap map
    ){
        List<Seat> seats = seatDao.getSeatsByIDAvailable(showId);
        if(seats.size() == 0){
            return "no-shows-available";
        }
        map.addAttribute("movieId", movieId);
        map.addAttribute("theatreId", theatreId);
        map.addAttribute("screenId",screenId);
        map.addAttribute("showId", showId);
        map.addAttribute("seats",seats);

        return "seats-list";
    }
}
