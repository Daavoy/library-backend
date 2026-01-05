package com.example.backend.services;

import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.DTO.UserBookDTO;
import com.example.backend.mapper.UserBookMapper;
import com.example.backend.models.*;
import com.example.backend.repositories.UserBookRepository;
import com.example.backend.repositories.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookService {

    private final BookService bookService;
    private final UserBookRepository userBookRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserBookMapper userBookMapper;
    private final Object lock = new Object();

    public UserBookService(BookService bookService,
                           UserBookRepository userBookRepository,
                           UserInfoRepository userInfoRepository,
                           UserBookMapper userBookMapper) {
        this.bookService = bookService;
        this.userBookRepository = userBookRepository;
        this.userInfoRepository = userInfoRepository;
        this.userBookMapper = userBookMapper;
    }

    public UserBookDTO addToLibrary(Long userId, AddUserBookDTO req) {
        Book book = bookService.getOrCreate(req.getTitle(), req.getAuthor(),req.getTotalPages());
        return saveUserBook(userId, book, req.getReadingStatus());
    }

    public UserBookDTO saveUserBook(Long userId, Book book, ReadingStatus status) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        boolean alreadyExists = userBookRepository.existsByUserAndBook(user, book);
        if (alreadyExists) {
            throw new IllegalStateException("Book already in library");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setStatus(status != null ? status : ReadingStatus.PLANNED);

        userBook = userBookRepository.save(userBook);  
        return userBookMapper.toDto(userBook);
    }

    public List<UserBookDTO> findAllDTO() {
        return userBookRepository.findAll()
                .stream()
                .map(userBookMapper::toDto)
                .toList();
    }

    public List<UserBookDTO> findAllForUser(Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return userBookRepository.findAllByUser(user)
                .stream()
                .map(userBookMapper::toDto)
                .toList();
    }

    public Optional<UserBookDTO> findByIdDTO(Long id) {
        return userBookRepository.findById(id)
                .map(userBookMapper::toDto);
    }

    @Transactional
    public void deleteById(Long id) {
        userBookRepository.deleteById(id);
    }
}
