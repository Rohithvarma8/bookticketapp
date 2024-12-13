package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Seat;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public List<Seat> getSeatsByIDAvailable(int showId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Seat s WHERE s.show.id = :showId AND s.isBooked = false";
            Query query = session.createQuery(hql, Seat.class);
            query.setParameter("showId", showId);
            return query.getResultList();
        }
    }


    public void deleteSeatById(int seatId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Seat seat = session.get(Seat.class, seatId);
            if (seat != null) {
                session.delete(seat);
            }
            tx.commit();
        }
    }

    public Seat getSeatById(int seatId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Seat.class, seatId);
        }
    }

    public void updateSeat(Seat seat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(seat);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update seat", e);
        }
    }






}
