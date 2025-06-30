package com.example.backend.controllers;

import com.example.backend.DTO.BookDTO;
import com.example.backend.DTO.BookFormDTO;
import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.models.Book;
import com.example.backend.services.BookService;
import exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> createBook(@ModelAttribute BookFormDTO form) throws IOException {
        BookUpdateDTO dto = new BookUpdateDTO(
                form.getTitle(),
                form.getAuthor(),
                form.getDescription(),
                form.getIsbn(),
                form.getPublicationYear()
        );

        byte[] thumbnail = form.getThumbnail() != null ? form.getThumbnail().getBytes() : null;

        Book saved = bookService.createBook(dto, thumbnail);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.mapToDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO, @RequestParam("thumbnail") MultipartFile thumbnail) {
        try {
            Book updatedBook = bookService.updateBook(id, bookUpdateDTO, thumbnail.getBytes());
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
