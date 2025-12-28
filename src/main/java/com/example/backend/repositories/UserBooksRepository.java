package com.example.backend.repositories;

import com.example.backend.models.ReadingStatus;
import com.example.backend.models.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {
    Optional<UserBooks> findByUserIdAndBookId(Long userId, Long bookId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
    List<UserBooks> findAllByUserId(Long userId);
    List<UserBooks> findAllByUserIdAndStatus(Long userId, ReadingStatus status);
}
