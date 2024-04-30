package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Long>  {
//    List<Author> findByPublished(boolean published);

//    List<Author> findByTitleContaining(String title);
}