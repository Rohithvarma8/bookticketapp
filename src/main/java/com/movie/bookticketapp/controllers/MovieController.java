package com.movie.bookticketapp.controllers;


import com.movie.bookticketapp.dao.MovieDao;
import com.movie.bookticketapp.models.Movie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    MovieDao movieDao;

    public static final String MOVIE_DIR = "/Users/rohithvarmadatla/IdeaProjects/BookTicketApp/src/main/resources/static/movieImages";

    @GetMapping("/adminPage")
    public String viewAdminPage(ModelMap map, HttpSession session) {
        List<Movie> movies = movieDao.findAllMovies(); // Fetch movies from the database
        map.addAttribute("movies", movies);// Pass the movie list to the view
        if (session == null) {
            // No session exists, redirect to login
            return "no-session";
        }

        // Fetch session attributes
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null || !role.equals("admin")) {
            // Invalidate session if it's invalid or unauthorized
            session.invalidate();
            return "unauthorised";
        }

        // Add session attributes to the view
        map.addAttribute("username", username);
        return "adminPage"; // Render the adminPage.html
    }

    @GetMapping("/addMovie")
    public String viewAddMovie(
            @ModelAttribute Movie movie,
            ModelMap map, HttpSession session
    ){
        if (session == null) {
            // No session exists, redirect to login
            return "no-session";
        }

        // Fetch session attributes
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null || !role.equals("admin")) {
            // Invalidate session if it's invalid or unauthorized
            session.invalidate();
            return "unauthorised";
        }

        // Add session attributes to the view
        map.addAttribute("username", username);
        map.addAttribute("movie", movie);
        return "movieForm";
    }

    @PostMapping("/addMovie")
    public String addMovieInDb(
            @ModelAttribute Movie movie,
            ModelMap map, HttpSession session
    ) {
        if (session == null) {
            // No session exists, redirect to login
            return "no-session";
        }

        // Fetch session attributes
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null || !role.equals("admin")) {
            // Invalidate session if it's invalid or unauthorized
            session.invalidate();
            return "unauthorised";
        }

        // Add session attributes to the view
        map.addAttribute("username", username);
        if(movie.getImage() == null || movie.getImage().isEmpty()){
            map.addAttribute("error", "please upload a movie image");
            return "redirect:/movieForm";
        }

        try {
            Path uploadPath = Paths.get(MOVIE_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = String.format(
                    "%s_%s_%s_%s%s",
                    movie.getTitle().replaceAll("\\s+", "_"),
                    movie.getActor().replaceAll("\\s+", "_"),
                    movie.getDirector().replaceAll("\\s+", "_"),
                    movie.getGenre().replaceAll("\\s+", "_"),
                    getExtension(movie.getImage().getOriginalFilename())
            );

            // Save the file
            Path filePath = uploadPath.resolve(fileName);
            movie.getImage().transferTo(filePath.toFile());

            // Optionally, save the image path in the database
            movie.setImagePath(fileName); // Clear the MultipartFile object to avoid issues
            movieDao.saveMovie(movie);

            // Add success message
            map.addAttribute("message", "Movie added successfully!");
            return "redirect:/adminPage";

        } catch (IOException e) {
            map.addAttribute("error", "Failed to upload movie image: " + e.getMessage());
            return "movieForm"; // Show the form with an error message
        }
    }
    private String getExtension(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        if (index != -1) {
            return originalFilename.substring(index);
        }
        return ""; // No extension
    }

}
