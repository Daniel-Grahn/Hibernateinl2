package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "TBL_BOOK")
public class Book {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

   @Column(name = "TITLE")
   private String title;

   @Column(name = "GENRE", length = 30)
   private String genre;

   @Column(name = "PUBLICATION_YEAR")
   private int publicationYear;

   // Many-to-Many relation with Reader
   @ManyToMany
   @JoinTable(name = "BOOK_READER", joinColumns = @JoinColumn(name = "BOOK_ID"), inverseJoinColumns = @JoinColumn(name = "READER_ID"))
   private Set<Reader> readers = new HashSet<>();

   public Book() {
   }

   public Book(String title, String genre, int publicationYear) {
      this.title = title;
      this.genre = genre;
      this.publicationYear = publicationYear;
   }

   public int getId() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getGenre() {
      return genre;
   }

   public int getPublicationYear() {
      return publicationYear;
   }

   public Set<Reader> getReaders() {
      return readers;
   }

   public void addReader(Reader reader) {
      this.readers.add(reader);
   }

   @Override
   public String toString() {
      return "Book [id=" + id + ", title=" + title + ", genre=" + genre + ", publicationYear=" + publicationYear + "]";
   }
}
