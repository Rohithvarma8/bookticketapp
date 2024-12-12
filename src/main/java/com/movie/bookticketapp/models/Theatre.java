//package com.movie.bookticketapp.models;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.Cascade;
//import java.util.List;
//
//@Entity
//@Table(name = "theatres")
//public class Theatre {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "theatre_name")
//    private String name;
//
//    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    private List<Screen> screens;
//
//    // Getters and Setters
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<Screen> getScreens() {
//        return screens;
//    }
//
//    public void setScreens(List<Screen> screens) {
//        this.screens = screens;
//    }
//}
package com.movie.bookticketapp.models;

import com.movie.bookticketapp.enums.Location;
import org.hibernate.annotations.Cascade;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "theatre_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Screen> screens;


    @ManyToMany(mappedBy = "theatres", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<MovieDetails> movies;


    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }

    public List<MovieDetails> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDetails> movies) {
        this.movies = movies;
    }
}
