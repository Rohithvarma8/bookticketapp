package com.movie.bookticketapp.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name = "movie_details")
public class MovieDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key for MovieDetails

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false, unique = true) // Foreign key in `movie_details` table
    private Movie movie;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_details_theatres",
            joinColumns = @JoinColumn(name = "movie_details_id"),
            inverseJoinColumns = @JoinColumn(name = "theatre_id")
    )
    private List<Theatre> theatres;


    // Getters and setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(List<Theatre> theatres) {
        this.theatres = theatres;
    }
}


