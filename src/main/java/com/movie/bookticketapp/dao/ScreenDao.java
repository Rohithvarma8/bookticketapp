package com.movie.bookticketapp.dao;


import com.movie.bookticketapp.models.Screen;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScreenDao {

    @Autowired
    SessionFactory sf;

    public void saveScreen(Screen screen){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(screen);
        tx.commit();
    }

}
