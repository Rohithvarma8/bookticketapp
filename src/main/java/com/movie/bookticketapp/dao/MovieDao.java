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

    public List<Movie> findMoviesWithPagination(int limit, int offset) {
        try (Session session = sf.openSession()) {
            String hql = "FROM Movie";
            Query<Movie> query = session.createQuery(hql, Movie.class);
            query.setFirstResult(offset); // OFFSET
            query.setMaxResults(limit);  // LIMIT
            return query.list();
        }
    }

    // Count total movies
    public long countMovies() {
        try (Session session = sf.openSession()) {
            String hql = "SELECT COUNT(m) FROM Movie m";
            Query<Long> query = session.createQuery(hql, Long.class);
            return query.uniqueResult();
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
