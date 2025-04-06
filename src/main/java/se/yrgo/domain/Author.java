package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "TBL_AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", length = 30)
    private String name;

    @Column(name = "NATIONALITY", length = 30)
    private String nationality;

    // One-to-Many relation with Book, unidirectional
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AUTHOR_ID") // Foreign key is in the Book table
    private Set<Book> books = new HashSet<>();

    public Author() {}

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Set<Book> getBooks() {
        return books;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", nationality=" + nationality + "]";
    }
}


