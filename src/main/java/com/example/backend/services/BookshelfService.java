package com.example.backend.services;

import com.example.backend.models.Book;
import com.example.backend.models.Bookshelf;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.BookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookshelfService  {
    @Autowired
    private BookshelfRepository bookshelfRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }
    public List<Bookshelf> getAllBookshelves() {
        return bookshelfRepository.findAll();
    }
    public Optional<Book> getBookFromBookshelf(Long bookshelfId, Long bookId) {
        Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                .orElseThrow(() -> new RuntimeException("Bookshelf not found"));
        return bookshelf.getBooks().stream().filter((book) -> book.getId().equals(bookId)).findFirst();
    }


    public Book addBookToShelf(Long shelfId, Book book){
        Bookshelf bookshelf = bookshelfRepository.findById(shelfId).orElseThrow(() ->
                new RuntimeException("Bookshelf not found")
        );
        book.setBookshelf(bookshelf);
        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, Book updatedBook) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() ->
                new RuntimeException("Book not found")
        );
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setThumbnail(updatedBook.getThumbnail());
        return bookRepository.save(existingBook);
    }
    public Book deleteBookFromShelf(Long shelfId, Book book) {
        Bookshelf bookshelf = bookshelfRepository.findById(shelfId)
                .orElseThrow(() -> new RuntimeException("Bookshelf not found"));
        bookshelf.getBooks().remove(book);
        bookshelfRepository.save(bookshelf);
        return book;
    }
}
