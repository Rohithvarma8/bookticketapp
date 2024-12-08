package com.movie.bookticketapp.dao;


import com.movie.bookticketapp.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

@Repository
public class RoleDao {

    @Autowired
    SessionFactory sf;

    public Role findRole(int role_id){
        try (Session session = sf.openSession()) {
            // HQL query to find Role by id
            String hql = "FROM Role r WHERE r.id = :role_id";
            Query<Role> query = session.createQuery(hql, Role.class);
            query.setParameter("role_id", role_id); // Bind the parameter
            return query.uniqueResult(); // Fetch a single result
        }
    }
}
