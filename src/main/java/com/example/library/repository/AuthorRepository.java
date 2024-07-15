package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, Long>  {

    @Query(value="SELECT * FROM author WHERE full_name ILIKE '%' || :keyword || '%'", nativeQuery=true)
    List<Author> findByKeyword(@Param("keyword") String keyword);

//    @Query(value="SELECT * FROM aut n WHERE n.uuid= ?1")
//    @Query(value = "SELECT * from author where guid.uuid=?1")
    @Query(value="SELECT * FROM author WHERE author.guid=?1", nativeQuery=true)
    Author findByUuid(UUID guid);

}