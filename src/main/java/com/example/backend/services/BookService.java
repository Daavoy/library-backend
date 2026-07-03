package com.example.backend.services;

import com.example.backend.DTO.BookRequest;
import com.example.backend.mapper.BookMapper;
import com.example.backend.models.Book;
import exceptions.BookNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.backend.repositories.BookRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // @Transactional on read operations tells Hibernate this is a read-only
    // operation — it can optimise accordingly and won't flush changes to the DB
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Returns Optional instead of throwing — let the controller decide
    // what HTTP response to give. The service shouldn't know about HTTP.
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book createBook(BookRequest request, byte[] thumbnail) {
        Book book = bookMapper.toEntity(request);
        // Only set thumbnail if one was actually provided
        if (thumbnail != null) {
            book.setThumbnail(thumbnail);
        }
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBookById(Long id) {
        // Verify the book exists before attempting delete,
        // so we can throw a meaningful exception if not found
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    @Transactional
    public Book updateBook(Long id, BookRequest request, byte[] thumbnail) {
        // findById inside a @Transactional means Hibernate tracks this entity —
        // updateEntity modifies it in place, save() persists those changes
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookMapper.updateEntity(request, book);

        if (thumbnail != null) {
            book.setThumbnail(thumbnail);
        }
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
    @Transactional(readOnly = true)
    public Optional<byte[]> getThumbnail(Long id) {
        return bookRepository.findThumbnailById(id);
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}