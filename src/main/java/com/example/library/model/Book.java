package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "publication")
    private Date publication;

    @Column(name = "genre")
    private String genre;

    private UUID guid;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name="author_id", nullable=false)
    private Author author;

    public Book(String name, Date publication, String genre, Author author) {
        this.name= name;
        this.publication = publication;
        this.genre = genre;
        this.author = author;
    }

    public void setUserNULL() {
        this.user = null;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", Publication='" + publication + '\'' +
                ", Genre='" + genre + '\'' +
                '}';
    }

}