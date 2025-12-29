package com.example.backend.services;


import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.models.Book;
import com.example.backend.models.UserBooks;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.UserBooksRepository;
import exceptions.BookNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBooksService {
    private final BookRepository bookRepository;
    private final UserBooksRepository userBooksRepository;
    @Autowired
    public UserBooksService(BookRepository bookRepository, UserBooksRepository userBooksRepository) {
        this.bookRepository = bookRepository;
        this.userBooksRepository = userBooksRepository;
    }

    public List<UserBooks> findAll() { return userBooksRepository.findAll(); }
    public Optional<UserBooks> findById(Long id) { return userBooksRepository.findById(id); }

    public UserBooks save(UserBooks userBooks) { return userBooksRepository.save(userBooks); }

    public void deleteById(Long id) { this.userBooksRepository.deleteById(id); }

    public boolean existByUserIdAndBookId(Long userId, Long bookId) {
        return userBooksRepository.findByUserIdAndBookId(userId, bookId).isPresent();
    }
    public List<UserBooks> findByUserId(Long userId) {
        return userBooksRepository.findAllByUserId(userId);
    }
    public UserBooks addToLibrary(Long userId, AddUserBookDTO req) {
        Book book;
        Long requestBookId = req.getBookId();
        if (requestBookId != null) {
            book = bookRepository.findById(requestBookId)
                    .orElseThrow(() -> new BookNotFoundException(requestBookId));
        }else {
            book = bookRepository.findByTitleIgnoreCaseAndAuthorIgnoreCase(req.getTitle(),req.getAuthor())
                    .orElseGet(() -> bookRepository.save(
                    new Book(req.getTitle(), req.getAuthor(), req.getTotalPages())
            ));
        }
    }
}
