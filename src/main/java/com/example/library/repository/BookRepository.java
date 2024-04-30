package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long>  {
    
//    List<Author> findByPublished(boolean published);

//    List<Author> findByTitleContaining(String title);
}