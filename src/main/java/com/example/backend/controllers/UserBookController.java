package com.example.backend.controllers;

import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.DTO.UserBookDTO;
import com.example.backend.DTO.UserBookUpdateDTO;
import com.example.backend.models.UserBook;
import com.example.backend.repositories.UserBookRepository;
import com.example.backend.services.UserBookService;
import com.example.backend.models.UserInfoDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBookController {

    private final UserBookService userBookService;
    private final UserBookRepository userBookRepository;

    public UserBookController(UserBookService userBookService, UserBookRepository userBookRepository) {
        this.userBookService = userBookService;
        this.userBookRepository = userBookRepository;
    }

    @GetMapping("/userBooks")
    public ResponseEntity<List<UserBookDTO>> getAllUserBooks() {
        return ResponseEntity.ok(userBookService.findAllDTO());
    }

    @GetMapping("/me/library")
    public ResponseEntity<List<UserBookDTO>> getMyLibrary(@AuthenticationPrincipal UserInfoDetails userDetails) {
        return ResponseEntity.ok(userBookService.findAllForUser(userDetails.getId()));
    }

    @GetMapping("/me/library/{bookId}")
    public ResponseEntity<UserBookDTO> getBookFromLibrary(@PathVariable Long bookId,
                                                          @AuthenticationPrincipal UserInfoDetails userDetails) {
        return userBookService.findAllForUser(userDetails.getId())
                .stream()
                .filter(dto -> dto.getBookId().equals(bookId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("userBooks/{id}")
    public ResponseEntity<UserBookDTO> getUserBookById(@PathVariable Long id) {
        return userBookRepository.findById(id).map(book->
                ResponseEntity.ok(new UserBookDTO(book)))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("userBooks/{id}")
    public ResponseEntity<UserBookDTO> updateUserBook(@PathVariable Long id, @RequestBody @Valid UserBookUpdateDTO userBookUpdateDTO) {
        return userBookRepository.findById(id)
                .map(userBook -> {
                    if (userBookUpdateDTO.getCurrentPage() != null) {
                        userBook.setCurrentPage(userBookUpdateDTO.getCurrentPage());
                    }
                    if (userBookUpdateDTO.getIsFavorite() != null) {
                        userBook.setIsFavorite(userBookUpdateDTO.getIsFavorite());
                    }
                    if (userBookUpdateDTO.getNotes() != null) {
                        userBook.setNotes(userBookUpdateDTO.getNotes());
                    }
                    userBookRepository.save(userBook);
                    return ResponseEntity.ok( new UserBookDTO(userBook));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/me/library")
    public ResponseEntity<UserBookDTO> addBookToLibrary(@AuthenticationPrincipal UserInfoDetails userDetails,
                                                        @RequestBody @Valid AddUserBookDTO request) {
        UserBookDTO addedBook = userBookService.addToLibrary(userDetails.getId(), request);
        return ResponseEntity.ok(addedBook);
    }

    @DeleteMapping("userBooks/{id}")
    public ResponseEntity<Void> deleteUserBook(@PathVariable Long id) {
        userBookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me/library/{bookId}")
    public ResponseEntity<Void> deleteBookFromLibrary(@PathVariable Long bookId,
                                                      @AuthenticationPrincipal UserInfoDetails userDetails) {
        userBookService.findAllForUser(userDetails.getId())
                .stream()
                .filter(dto -> dto.getBookId().equals(bookId))
                .findFirst()
                .ifPresent(dto -> userBookService.deleteById(dto.getId()));
        return ResponseEntity.noContent().build();
    }
}
