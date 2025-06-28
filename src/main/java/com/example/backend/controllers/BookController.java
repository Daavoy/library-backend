package com.example.backend.controllers;

import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.models.Book;
import com.example.backend.services.BookService;
import exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookUpdateDTO bookUpdateDTO) {
        Book createdBook = bookService.createBook(bookUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        try {
            Book updatedBook = bookService.updateBook(id, bookUpdateDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
