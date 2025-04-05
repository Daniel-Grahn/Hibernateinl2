package se.yrgo.test;

import se.yrgo.domain.*;

import jakarta.persistence.*;

public class Create {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        //setUp(em);
        tx.begin();


        tx.commit();
        em.close();
    }

    public static void setUp(EntityManager em){
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Skapa författare
        Author a1 = new Author("Newton", "English");
        Author a2 = new Author("Daniel", "Swedish");
        Author a3 = new Author("Albert", "German");

        // Skapa böcker
        Book b1 = new Book("The Gravitation", "Science", 1777);
        Book b2 = new Book("Dracula", "Thriller", 1897);
        Book b3 = new Book("The Cruel Prince", "Fantasy", 2018);
        Book b4 = new Book("Hunger Games", "Fantasy", 2008);
        Book b5 = new Book("Frankenstein", "Horror", 1813);

        // Koppla böcker till författare
        a1.addBook(b1);
        a1.addBook(b2);
        a2.addBook(b3);
        a3.addBook(b4);
        a3.addBook(b5);

        // Skapa läsare
        Reader r1 = new Reader("Anderson", "AndAnder@gmail.se");
        Reader r2 = new Reader("Peterson", "PetPer@gmail.se");
        Reader r3 = new Reader("Jungfru", "FruJung@gmail.se");

        // Koppla läsare till böcker
        b1.addReader(r1);
        b1.addReader(r2);
        b2.addReader(r2);
        b3.addReader(r3);
        b4.addReader(r1);
        b5.addReader(r3);

        // Persist alla objekt
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);

        em.persist(r1);
        em.persist(r2);
        em.persist(r3);

        tx.commit();
        em.close();
    }
}

