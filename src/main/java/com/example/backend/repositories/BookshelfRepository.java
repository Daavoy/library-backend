package com.example.backend.repositories;

import com.example.backend.models.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {
    void removeById(Long id);
}
