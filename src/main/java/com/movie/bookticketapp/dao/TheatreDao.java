package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Theatre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TheatreDao {

    @Autowired
    SessionFactory sf;

    public void saveTheatre(Theatre theatre){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(theatre);
        tx.commit();
    }

}
