package com.example.backend.services;

import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.models.Book;
import exceptions.BookNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repositories.BookRepository;

import java.util.List;
import java.util.Optional;
@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    private BookUpdateDTO mapToDTO(Book book) {
        return new BookUpdateDTO(book.getTitle(), book.getAuthor(), book.getDescription(), book.getIsbn());
    }
    private Book mapToEntity(BookUpdateDTO bookUpdateDTO) {
        return new Book(bookUpdateDTO.getTitle(), bookUpdateDTO.getAuthor(), bookUpdateDTO.getDescription(), bookUpdateDTO.getIsbn());
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    public Book createBook(BookUpdateDTO bookUpdateDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookUpdateDTO, book);
        return bookRepository.save(book);
    }
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    public Book updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BeanUtils.copyProperties(bookUpdateDTO, existingBook, "id");
        return bookRepository.save(existingBook);
    }

}
