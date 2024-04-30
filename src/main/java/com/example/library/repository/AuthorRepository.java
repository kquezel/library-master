package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>  {

    @Query(value="SELECT * FROM author WHERE full_name ILIKE '%' || :keyword || '%'", nativeQuery=true)
    List<Author> findByKeyword(@Param("keyword") String keyword);
}