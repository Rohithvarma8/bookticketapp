package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Seat;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveSeat(Seat seat) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(seat);
            session.getTransaction().commit();
        }
    }

    public List<Seat> getSeatsByShowId(int showId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Seat WHERE show.id = :showId";
            Query query = session.createQuery(hql, Seat.class);
            query.setParameter("showId", showId);
            return query.getResultList();
        }
    }
}
