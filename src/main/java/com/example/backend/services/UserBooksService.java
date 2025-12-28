package com.example.backend.services;


import com.example.backend.models.UserBooks;
import com.example.backend.repositories.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBooksService {
    private final UserBooksRepository userBooksRepository;
    @Autowired
    public UserBooksService(UserBooksRepository userBooksRepository) {
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
}
