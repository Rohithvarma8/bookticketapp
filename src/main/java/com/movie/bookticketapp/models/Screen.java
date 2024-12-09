package com.movie.bookticketapp.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "screenNumber")
    private String screenNumbers;

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getScreenNumbers() {
        return screenNumbers;
    }

    public void setScreenNumbers(String screenNumbers) {
        this.screenNumbers = screenNumbers;
    }
}
