//package com.movie.bookticketapp.dao;
//
//
//import com.movie.bookticketapp.models.Screen;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class ScreenDao {
//
//    @Autowired
//    SessionFactory sf;
//
//    public void saveScreen(Screen screen){
//        Session session = sf.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(screen);
//        tx.commit();
//    }
//
//}


package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Screen;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScreenDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Save or update a screen
    public void saveScreen(Screen screen) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(screen);
            tx.commit();
        }
    }

    // Get all screens
    public List<Screen> getAllScreens() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Screen";
            Query<Screen> query = session.createQuery(hql, Screen.class);
            return query.list();
        }
    }

    // Get screens by theatre
    public List<Screen> getScreensByTheatreId(int theatreId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Screen WHERE theatre.id = :theatreId";
            Query<Screen> query = session.createQuery(hql, Screen.class);
            query.setParameter("theatreId", theatreId);
            return query.list();
        }
    }

    public Screen getScreenById(int screenId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Screen.class, screenId); // Efficiently fetches by primary key
        }
    }
}
