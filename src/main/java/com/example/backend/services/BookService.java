package com.example.backend.services;

import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.mapper.BookMapper;
import com.example.backend.models.Book;
import exceptions.BookNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.backend.repositories.BookRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final Object bookCreationLock = new Object();
    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(BookUpdateDTO dto, byte[] thumbnail) {
        Book book = bookMapper.toEntity(dto);
        book.setThumbnail(thumbnail);
        return bookRepository.save(book);
    }
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    public Book updateBook(Long id, BookUpdateDTO dto, byte[] thumbnail) {
        Book book = getBookById(id);
        bookMapper.updateEntity(dto, book);
        book.setThumbnail(thumbnail);
        return bookRepository.save(book);
    }

    @Transactional
    public Book getOrCreate(String title, String author, int totalPages) {
        List<Book> books =
                bookRepository.findAllByTitleIgnoreCaseAndAuthorIgnoreCase(title, author);

        if (!books.isEmpty()) {
            return books.get(0);
        }
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setTotalPages(totalPages);

        return bookRepository.save(book);
    }
}