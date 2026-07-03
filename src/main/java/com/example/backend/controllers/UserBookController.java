package com.example.backend.controllers;

import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.DTO.UserBookDTO;
import com.example.backend.DTO.UserBookUpdateDTO;
import com.example.backend.services.UserBookService;
import com.example.backend.models.UserInfoDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBookController {

    private final UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }


    @GetMapping("/me/library")
    public ResponseEntity<List<UserBookDTO>> getMyLibrary(@AuthenticationPrincipal UserInfoDetails userDetails) {
        return ResponseEntity.ok(userBookService.findAllForUser(userDetails.getId()));
    }

    @GetMapping("/me/library/{bookId}")
    public ResponseEntity<UserBookDTO> getBookFromLibrary(@PathVariable Long bookId,
                                                          @AuthenticationPrincipal UserInfoDetails userDetails) {
        return userBookService.findForUserAndBook(userDetails.getId(),bookId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me/library/{bookId}")
    public ResponseEntity<UserBookDTO> updateMyLibrary(@AuthenticationPrincipal UserInfoDetails userDetails,
                                                       @PathVariable Long bookId,
                                                       @RequestBody @Valid UserBookUpdateDTO request) {
        return userBookService.updateForUser(userDetails.getId(), bookId, request).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/me/library")
    public ResponseEntity<UserBookDTO> addBookToLibrary(@AuthenticationPrincipal UserInfoDetails userDetails,
                                                        @RequestBody @Valid AddUserBookDTO request) {
        UserBookDTO addedBook = userBookService.addToLibrary(userDetails.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @DeleteMapping("/me/library/{bookId}")
    public ResponseEntity<Void> deleteBookFromLibrary(@PathVariable Long bookId,
                                                      @AuthenticationPrincipal UserInfoDetails userDetails) {
        userBookService.deleteForUser(userDetails.getId(), bookId);
        return ResponseEntity.noContent().build();
    }
}
