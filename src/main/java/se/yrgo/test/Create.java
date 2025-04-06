package se.yrgo.test;

import se.yrgo.domain.*;

import javax.security.auth.Subject;

import jakarta.persistence.*;

import java.util.*;

public class Create {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        setUp(em); //Do setUp first and then the task's. OBS!!! persistence.xml (hibernate.hbm2ddl.auto has the value "create") 

        tx.begin();
        // task2(em);
        // task3(em);
        // task4(em);
        // task5(em);
        // task6(em);

        tx.commit();
        em.close();
    }

    //task1 but i see it more like a set up.
    public static void setUp(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
    
        // Authors
        Author a1 = new Author("J.K. Rowling", "British");
        Author a2 = new Author("George R.R. Martin", "American");
        Author a3 = new Author("Mary Shelley", "British");
        Author a4 = new Author("Daniel Defoe", "British");
    
        // Books
        Book b1 = new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 1997);
        Book b2 = new Book("Harry Potter and the Chamber of Secrets", "Fantasy", 1998);
        Book b3 = new Book("A Game of Thrones", "Fantasy", 1996);
        Book b4 = new Book("A Clash of Kings", "Fantasy", 1998);
        Book b5 = new Book("Frankenstein", "Horror", 1818);
        Book b6 = new Book("Robinson Crusoe", "Adventure", 1719);
    
        // Assign books to authors
        a1.addBook(b1);
        a1.addBook(b2);

        a2.addBook(b3);
        a2.addBook(b4);

        a3.addBook(b5);

        a4.addBook(b6);
    
        // Readers
        Reader r1 = new Reader("Emma Svensson", "emma.svensson@mail.com");
        Reader r2 = new Reader("Liam Johansson", "liam.j@mail.com");
        Reader r3 = new Reader("Sofia Karlsson", "sofia.karlsson@mail.com");
        Reader r4 = new Reader("Noah Eriksson", "noah.eriksson@mail.com");
    
        // Assign readers to books (except Robinson Crusoe - no readers yet)
        b1.addReader(r1);
        b1.addReader(r2);

        b2.addReader(r3);

        b3.addReader(r1);
        b3.addReader(r4);

        b4.addReader(r2);

        b5.addReader(r3);
        b5.addReader(r4);
        // b6 remains unread
    
        // Persist authors
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
    
        // Persist readers
        em.persist(r1);
        em.persist(r2);
        em.persist(r3);
        em.persist(r4);
    
        tx.commit();
        em.close();
    }
    

    //Get all books by a specific author (JPQL)
    public static void task2(EntityManager em) {
        Author theAuthor = em.find(Author.class, 1); //J.K. Rowling 
        System.out.println(theAuthor.toString() +"\n");

        Set<Book> theAuthorsBooks = theAuthor.getBooks();

        for (Book book : theAuthorsBooks) {
            System.out.println(book);
        }
    }

    //Get all readers who have read a particular book (member of)
    public static void task3(EntityManager em) {
        Book theBook = em.find(Book.class, 5); //A Game of Thrones
        Query query = em.createQuery("FROM Reader reader WHERE :book member of reader.books");
        query.setParameter("book", theBook);

        List<Reader> readers = query.getResultList();

        for (Reader reader : readers) {
            System.out.println("reader: " + reader.getName() + " " + reader.getBooks());
        }
    }

    //Get authors whose books have been read by at least one reader (join)
    public static void task4(EntityManager em) {
        // inner join
        Query query = em.createQuery(
                "SELECT DISTINCT author " +
                "FROM Author author " +
                "JOIN author.books book " +
                "JOIN book.readers reader");

        List<Author> authors = query.getResultList(); // no Daniel Defoe

        for (Author author : authors) {
            System.out.println(author);
        }
    }

    //Count the number of books per author (Aggregation Query)
    public static void task5(EntityManager em) {
        Query query = em.createQuery(
                "SELECT author.name, author.id, COUNT(book) " +
                "FROM Author author " +
                "JOIN author.books book " +
                "GROUP BY author.name, author.id " +
                "ORDER BY author.id");

        
        List<Object[]> results = (List<Object[]>) query.getResultList();

        for (Object[] result : results) {
            System.out.println("Author (id: " + result[1] + "): " + result[0] + ", has " + result[2] + " books.");
        }
    }

    //Named Query - Retrieve books by genre
    public static void task6(EntityManager em) {
        String chooseGenre = "Fantasy";

        List<Object[]> authorAndBook = em.createNamedQuery("searchByGenre")
                                         .setParameter("genre", chooseGenre)
                                         .getResultList();

        System.out.println("chosen genre: " + chooseGenre);
        for (Object[] obj : authorAndBook) {
            Author theAuthor = (Author) obj[0];
            Book theBook = (Book) obj[1];
            System.out.println("Author: " + theAuthor.getName() +" (id: "+ theAuthor.getId() +"), "+ "Booktitle: "+ theBook.getTitle());
        }
    }
}