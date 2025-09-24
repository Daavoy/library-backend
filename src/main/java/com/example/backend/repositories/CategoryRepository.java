package com.example.backend.repositories;


import com.example.backend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByCategoryName(String name);
    Optional<Category> findById(long id);
    void deleteById(long id);
}
