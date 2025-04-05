package se.yrgo.test;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import se.yrgo.domain.Book;

public class Delet {
        private static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        SessionFactory sf = getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Book myBook = (Book)session.get(Book.class, 3);
        session.delete(myBook);
        tx.commit();
        session.close();

        
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}
