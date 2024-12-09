package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Seat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SeatDao {

    @Autowired
    SessionFactory sf;

    public void saveSeat(Seat seat){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(seat);
        tx.commit();
    }


}
