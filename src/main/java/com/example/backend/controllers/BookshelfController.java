package com.example.backend.controllers;

import com.example.backend.models.Book;
import com.example.backend.models.Bookshelf;
import com.example.backend.repositories.BookshelfRepository;
import com.example.backend.services.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookshelf")
public class BookshelfController {
    @Autowired
    private BookshelfService bookshelfService;
    @Autowired
    private BookshelfRepository bookshelfRepository;
    @GetMapping
    public ResponseEntity<List<Bookshelf>> getBookShelves(){
        List<Bookshelf> bookshelves = bookshelfService.getAllBookshelves();
        return ResponseEntity.ok(bookshelves);
    }
    @PostMapping
    public ResponseEntity<?> addBookshelf(Bookshelf bookshelf){
        return ResponseEntity.ok(bookshelfRepository.save(bookshelf));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookFromShelf(@PathVariable("id") Long shelfId, @RequestParam long bookId){
        return ResponseEntity.ok(bookshelfService.getBookFromBookshelf(shelfId,bookId));
    }

}
