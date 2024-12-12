package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.MovieDetails;
import jakarta.transaction.Transactional;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void saveMovieDetails(MovieDetails movieDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(movieDetails); // Save the MovieDetails
            tx.commit();
        }
    }

    public List<MovieDetails> getAllMovieDetails() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM MovieDetails";
            Query<MovieDetails> query = session.createQuery(hql, MovieDetails.class);
            return query.list();
        }
    }

    // Fetch MovieDetails by Movie ID
    public MovieDetails getMovieDetailsByMovieId(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT md FROM MovieDetails md JOIN FETCH md.theatres WHERE md.movie.id = :movieId";
            Query<MovieDetails> query = session.createQuery(hql, MovieDetails.class);
            query.setParameter("movieId", movieId);
            return query.uniqueResult(); // Guaranteed unique due to unique constraint
        }
    }

    // Update movie details
    public void updateMovieDetails(MovieDetails updatedDetails) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(updatedDetails); // Update the movieDetails object
            tx.commit();
        }
    }

    // Delete movie details by movieId
    public void deleteMovieDetailsByMovieId(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "DELETE FROM MovieDetails WHERE movie.id = :movieId";
            Query<?> query = session.createQuery(hql);
            query.setParameter("movieId", movieId);
            query.executeUpdate();
            tx.commit();
        }
    }

}

