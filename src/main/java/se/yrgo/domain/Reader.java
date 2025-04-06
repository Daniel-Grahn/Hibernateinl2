package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "TBL_READER")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    // Many-to-Many relation with Book
    @ManyToMany(mappedBy = "readers")
    private Set<Book> books = new HashSet<>();

    public Reader() {}

    public Reader(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Reader [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}

