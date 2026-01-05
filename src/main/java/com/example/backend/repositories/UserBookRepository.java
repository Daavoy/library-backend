package com.example.backend.repositories;

import com.example.backend.DTO.UserBookDTO;
import com.example.backend.models.Book;
import com.example.backend.models.ReadingStatus;
import com.example.backend.models.UserBook;
import com.example.backend.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    void deleteByUserIdAndBookId(Long userId, Long bookId);
    List<UserBook> findAllByUserIdAndStatus(Long userId, ReadingStatus status);
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    boolean existsByUserAndBook(UserInfo user, Book book);
    Collection<UserBook> findAllByUser(UserInfo user);
}
