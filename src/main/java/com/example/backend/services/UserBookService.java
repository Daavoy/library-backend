package com.example.backend.services;

import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.DTO.UserBookDTO;
import com.example.backend.DTO.UserBookUpdateDTO;
import com.example.backend.mapper.UserBookMapper;
import com.example.backend.models.*;
import com.example.backend.repositories.UserBookRepository;
import com.example.backend.repositories.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookService {

    private final BookService bookService;
    private final UserBookRepository userBookRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserBookMapper userBookMapper;


    public UserBookService(BookService bookService,
                           UserBookRepository userBookRepository,
                           UserInfoRepository userInfoRepository,
                           UserBookMapper userBookMapper) {
        this.bookService = bookService;
        this.userBookRepository = userBookRepository;
        this.userInfoRepository = userInfoRepository;
        this.userBookMapper = userBookMapper;
    }

    @Transactional
    public UserBookDTO addToLibrary(Long userId, AddUserBookDTO req) {
        Book book = bookService.getOrCreate(req.getTitle(), req.getAuthor(),req.getTotalPages());
        return saveUserBook(userId, book, req.getReadingStatus());
    }

    @Transactional
    public UserBookDTO saveUserBook(Long userId, Book book, ReadingStatus status) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        boolean alreadyExists = userBookRepository.existsByUserIdAndBookId(user.getId(), book.getId());
        if (alreadyExists) {
            throw new IllegalStateException("Book already in library");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setStatus(status != null ? status : ReadingStatus.PLANNED);

        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    @Transactional(readOnly = true)
    public List<UserBookDTO> findAllDTO() {
        return userBookRepository.findAll()
                .stream()
                .map(userBookMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserBookDTO> findAllForUser(Long userId) {

        return userBookRepository.findAllByUserId(userId)
                .stream()
                .map(userBookMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserBookDTO> findForUserAndBook(Long userId, Long bookId) {
        return userBookRepository.findByUserIdAndBookId(userId,bookId).map(userBookMapper::toDto);
    }

    @Transactional
    public Optional<UserBookDTO> updateForUser(Long userId, Long bookId, UserBookUpdateDTO dto) {
        return userBookRepository.findByUserIdAndBookId(userId,bookId).map(userBook -> {
            if (dto.getCurrentPage() != null) {
                userBook.setCurrentPage(dto.getCurrentPage());
            }
            if (dto.getIsFavorite() != null) {
                userBook.setIsFavorite(dto.getIsFavorite());
            }
            if (dto.getNotes() != null) {
                userBook.setNotes(dto.getNotes());
            }
            return userBookMapper.toDto(userBookRepository.save(userBook));
        });
    }

    @Transactional
    public void deleteForUser(Long userId, Long bookId) {
        userBookRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    @Transactional
    public void deleteById(Long id) {
        userBookRepository.deleteById(id);
    }
}
