package com.movie.bookticketapp.controllers;

import com.movie.bookticketapp.dao.MovieDao;
import com.movie.bookticketapp.dao.MovieDetailsDao;
import com.movie.bookticketapp.dao.TheatreDao;
import com.movie.bookticketapp.models.Movie;
import com.movie.bookticketapp.models.MovieDetails;
import com.movie.bookticketapp.models.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movieDetails")
public class MovieDetailsController {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieDetailsDao movieDetailsDao;

    @Autowired
    private TheatreDao theatreDao;

    // GET: Show the form to add movie details
    @GetMapping("/add")
    public String showAddMovieDetailsForm(
            @RequestParam("movieId") int movieId,
            @ModelAttribute MovieDetails movieDetails,
            ModelMap map) {
        // Fetch the movie using the movieId
        Movie movie = movieDao.getMovieById(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found with ID: " + movieId);
        }


        // Set the movie in MovieDetails and prepare data for the form
        movieDetails.setMovie(movie);
        List<Theatre> theatres = theatreDao.getAllTheatres();
        map.addAttribute("movieDetails", movieDetails);
        map.addAttribute("theatres", theatres);
        return "add-movie-details";
    }

    // POST: Save movie details
    @PostMapping("/add")
    public String addMovieDetails(
            @RequestParam("movieId") int movieId,
            @RequestParam List<Integer> theatreIds) {
        // Fetch the Movie by ID
        Movie movie = movieDao.getMovieById(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found with ID: " + movieId);
        }

        // Create and populate MovieDetails
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setMovie(movie); // Set the relationship

        // Fetch the selected theatres
        List<Theatre> selectedTheatres = theatreDao.getTheatresByIds(theatreIds);
        movieDetails.setTheatres(selectedTheatres);

        // Save MovieDetails
        movieDetailsDao.saveMovieDetails(movieDetails);

        return "redirect:/movieDetails/list?movieId=" + movieId;

    }

    @GetMapping("/list")
    public String listMovieDetails(@RequestParam("movieId") int movieId, ModelMap map) {
        MovieDetails movieDetails = movieDetailsDao.getMovieDetailsByMovieId(movieId);
        if (movieDetails == null) {
            return "redirect:/movieDetails/add?movieId=" + movieId;
        }
        map.addAttribute("movieDetails", movieDetails);
        return "list-movie-details"; // The template name
    }

    @GetMapping("/update")
    public String showUpdateMovieDetailsForm(@RequestParam("movieId") int movieId, ModelMap map) {
        MovieDetails movieDetails = movieDetailsDao.getMovieDetailsByMovieId(movieId);
        if (movieDetails == null) {
            throw new IllegalArgumentException("No MovieDetails found for Movie ID: " + movieId);
        }
        map.addAttribute("movieDetails", movieDetails);
        map.addAttribute("allTheatres", theatreDao.getAllTheatres()); // For theatre selection
        return "update-movie-details"; // Template for the update form
    }

    // Handle the update form submission
    @PostMapping("/update")
    public String updateMovieDetails(
            @RequestParam("movieId") int movieId,
            @RequestParam("theatreIds") List<Integer> theatreIds) {

        MovieDetails movieDetails = movieDetailsDao.getMovieDetailsByMovieId(movieId);
        if (movieDetails == null) {
            throw new IllegalArgumentException("No MovieDetails found for Movie ID: " + movieId);
        }

        // Update the theatre list
        List<Theatre> selectedTheatres = theatreDao.getTheatresByIds(theatreIds);
        movieDetails.setTheatres(selectedTheatres);

        // Save the updated details
        movieDetailsDao.updateMovieDetails(movieDetails);

        return "redirect:/movieDetails/list?movieId=" + movieId;
    }

    @GetMapping("/delete")
    public String deleteMovieDetails(@RequestParam("movieId") int movieId) {
        movieDetailsDao.deleteMovieDetailsByMovieId(movieId);
        return "redirect:/adminPage";
    }


}
