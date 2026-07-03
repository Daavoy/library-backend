package com.example.backend.repositories;

import com.example.backend.models.UserBook;
import com.example.backend.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    Collection<UserBook> findAllByUserId(Long userId);
    Long user(UserInfo user);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
