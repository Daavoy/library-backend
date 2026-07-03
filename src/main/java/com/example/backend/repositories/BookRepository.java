package com.example.backend.repositories;

import com.example.backend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    List<Book> findAllByTitleIgnoreCaseAndAuthorIgnoreCase(
            String title,
            String author
    );
    @Query("SELECT b.thumbnail FROM Book b WHERE b.id = :id")
    Optional<byte[]> findThumbnailById(@Param("id") Long id);
}

