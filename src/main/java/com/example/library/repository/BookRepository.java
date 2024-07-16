package com.example.library.repository;

import com.example.library.model.Author;
import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long>  {

    @Query(value="SELECT * FROM book WHERE name ILIKE '%' || :keyword || '%'", nativeQuery=true)
    List<Book> findByKeyword(@Param("keyword") String keyword);

    @Query(value="SELECT * FROM book WHERE book.guid=?1", nativeQuery=true)
    Optional<Book> findByUuid(UUID guid);

}