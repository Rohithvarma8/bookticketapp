package com.movie.bookticketapp.dao;


import com.movie.bookticketapp.models.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDao {

    @Autowired
    SessionFactory sf;

    //saving movie in database
    public void saveMovie(Movie movie){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(movie);
        tx.commit();
    }

    public List<Movie> findAllMovies() {
        try (Session session = sf.openSession()) {
            String hql = "FROM Movie";
            Query<Movie> query = session.createQuery(hql, Movie.class);
            return query.list();
        }
    }

    public Movie getMovieById(int movieId) {
        try (Session session = sf.openSession()) {
            Movie m = session.get(Movie.class, movieId);// Fetch movie by primary key
            System.out.println(m.getId());
            return m;
        }
    }


}
