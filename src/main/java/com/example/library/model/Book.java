package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "name")
    private String name;

    @Column(name = "publication")
    private Date publication;

    @Column(name = "genre")
    private String genre;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name="author_id", nullable=false)
    private Author author;

    public Book(String name, Date publication, String genre) {
        this.name= name;
        this.publication = publication;
        this.genre = genre;
    }

    public Book() {
    }


    //… getters and setters


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {

        this.author = author;

    }


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUserNULL() {
        this.user = null;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", Name='" + name + '\'' +
                ", Publication='" + publication + '\'' +
                ", Genre='" + genre + '\'' +
                '}';
    }

}