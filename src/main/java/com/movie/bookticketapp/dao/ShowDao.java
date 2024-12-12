package com.movie.bookticketapp.dao;

import com.movie.bookticketapp.models.Show;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShowDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveShow(Show show) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(show);
            session.getTransaction().commit();
        }
    }

    public List<Show> getShowsByScreenId(int screenId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Show WHERE screen.id = :screenId";
            Query query = session.createQuery(hql, Show.class);
            query.setParameter("screenId", screenId);
            return query.getResultList();
        }
    }

    public Show getShowById(int showId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Show.class, showId); // Efficiently fetch by primary key
        }
    }
}
