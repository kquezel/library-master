package com.example.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "biography")
    private String biography;

    private UUID guid;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> book = new ArrayList<>();

    public Author(String fullName, Date birth, String biography) {
        this.fullName = fullName;
        this.birth = birth;
        this.biography = biography;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author() {
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birth='" + birth + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }

}