package com.movie.bookticketapp;

import com.movie.bookticketapp.models.*;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class BookTicketAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookTicketAppApplication.class, args);
    }

    @Bean
    public SessionFactory getSessionFactory(){
        Map<String, Object> settings = new HashMap<>();

        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/bookticketdb");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "RoHi@8888");

        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        settings.put("hibernate.dialect.storage_engine", "innodb");
        settings.put("hibernate.show-sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();
        MetadataSources metaDataSources = new MetadataSources(serviceRegistry);
        metaDataSources.addPackage("com.movie.bookticketapp.models");
        metaDataSources.addAnnotatedClass(User.class);
        metaDataSources.addAnnotatedClass(Role.class);
        metaDataSources.addAnnotatedClass(Movie.class);
        metaDataSources.addAnnotatedClass(Screen.class);
        metaDataSources.addAnnotatedClass(Seat.class);
        metaDataSources.addAnnotatedClass(Theatre.class);
        metaDataSources.addAnnotatedClass(Show.class);
        metaDataSources.addAnnotatedClass(MovieDetails.class);
        Metadata metaData = metaDataSources.buildMetadata();

        return metaData.getSessionFactoryBuilder().build();
    }


}
