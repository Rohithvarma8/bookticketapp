package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Theatre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TheatreDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Saving a theatre
    public void saveTheatre(Theatre theatre) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(theatre);
            tx.commit();
        }
    }

    // Retrieving all theatres
    public List<Theatre> getAllTheatres() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Theatre";
            Query<Theatre> query = session.createQuery(hql, Theatre.class);
            return query.list();
        }
    }

    // Retrieving a theatre by ID
    public Theatre getTheatreById(int theatreId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Theatre.class, theatreId); // Efficiently fetch the theatre by its primary key
        }
    }

    public List<Theatre> getTheatresByIds(List<Integer> theatreIds) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Theatre WHERE id IN (:theatreIds)";
            Query<Theatre> query = session.createQuery(hql, Theatre.class);
            query.setParameter("theatreIds", theatreIds);
            return query.list();
        }
    }

    public void deleteTheatreById(int theatreId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            // Remove join table entries
            String hql = "DELETE FROM movie_details_theatres WHERE theatre_id = :theatreId";
            session.createNativeQuery(hql).setParameter("theatreId", theatreId).executeUpdate();

            // Remove the theatre
            Theatre theatre = session.get(Theatre.class, theatreId);
            if (theatre != null) {
                session.remove(theatre);
            }

            tx.commit();
        }
    }

}
