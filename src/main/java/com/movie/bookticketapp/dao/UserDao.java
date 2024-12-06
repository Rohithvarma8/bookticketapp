package com.movie.bookticketapp.dao;


import com.movie.bookticketapp.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    SessionFactory sf;

    //login username exists
    public User findByUserName(String userName) {
        try (Session session = sf.openSession()) {
            // HQL query to fetch the user by userName
            Query<User> query = session.createQuery(
                    "FROM User u WHERE u.userName = :userName", User.class);
            query.setParameter("userName", userName);

            // Return the unique result or null if no match found
            return query.uniqueResult();
        }
    }

    //db lookup for email
    public boolean isUsernameExists(String userName) {
        try (Session session = sf.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.userName = :userName", Long.class);
            query.setParameter("userName", userName);
            Long count = query.uniqueResult();
            return count > 0; // Returns true if username exists
        }
    }

    public void saveUser(User user){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
    }


}
