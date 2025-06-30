package com.example.backend.services;

import com.example.backend.DTO.BookDTO;
import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.models.Book;
import exceptions.BookNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repositories.BookRepository;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());

        if (book.getThumbnail() != null) {
            String base64 = Base64.getEncoder().encodeToString(book.getThumbnail());
            dto.setThumbnailBase64(base64);
        }
        return dto;
    }
    private Book mapToEntity(BookUpdateDTO bookUpdateDTO) {
        return new Book(bookUpdateDTO.getTitle(), bookUpdateDTO.getAuthor(), bookUpdateDTO.getDescription(), bookUpdateDTO.getIsbn(), bookUpdateDTO.getPublicationYear());
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(BookUpdateDTO bookDTO, byte[] thumbnail) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        book.setThumbnail(thumbnail);
        return bookRepository.save(book);
    }
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    public Book updateBook(Long id, BookUpdateDTO bookUpdateDTO, byte[] thumbnail) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BeanUtils.copyProperties(bookUpdateDTO, existingBook, "id");
        return bookRepository.save(existingBook);
    }

}
