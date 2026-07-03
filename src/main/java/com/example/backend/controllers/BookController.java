package com.example.backend.controllers;

import com.example.backend.DTO.BookRequest;
import com.example.backend.DTO.BookResponse;
import com.example.backend.mapper.BookMapper;
import com.example.backend.models.Book;
import com.example.backend.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {


    private final BookService bookService;
    private final BookMapper bookMapper;
    private static final int DEFAULT_LIMIT_SIZE = 100;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id)
                .map(bookMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) {
        return bookService.getThumbnail(id)
                .map(thumbnail -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(thumbnail))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponse> createBook(@Valid @ModelAttribute BookRequest request) throws IOException {

        byte[] thumbnail = request.getThumbnail() != null
                ? request.getThumbnail().getBytes()
                : null;

        Book saved = bookService.createBook(request, thumbnail);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,  @Valid @ModelAttribute BookRequest request) throws IOException {

        byte[] thumbnail = request.getThumbnail() != null
                ? request.getThumbnail().getBytes()
                : null;
        Book updated = bookService.updateBook(id, request, thumbnail);
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.toResponse(updated));
    }

    @GetMapping()
    public ResponseEntity<Page<BookResponse>> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, Math.min(size, DEFAULT_LIMIT_SIZE));
        return ResponseEntity.ok(bookService.getAllBooks(pageable).map(bookMapper::toResponse));
    }

}
