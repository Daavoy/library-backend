package com.example.backend.controllers;


import com.example.backend.DTO.AddUserBookDTO;
import com.example.backend.models.UserBooks;
import com.example.backend.models.UserInfoDetails;
import com.example.backend.services.UserBooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBooksController {
    @Autowired
    private UserBooksService userBooksService;

    @GetMapping("/userBooks")
    public ResponseEntity<List<UserBooks>> getAllUserBooks() {
        return ResponseEntity.ok(userBooksService.findAll());
    }

    @GetMapping("/me/library")
    public ResponseEntity<List<UserBooks>> getMyLibrary(@AuthenticationPrincipal UserInfoDetails user) {
        List<UserBooks> library = userBooksService.findByUserId(user.getId());
        return ResponseEntity.ok(library);
    }
    @GetMapping("/me/library/{id}")
    public ResponseEntity<UserBooks> getBookFromLibrary(@PathVariable Long id, @AuthenticationPrincipal UserInfoDetails user) {
        List<UserBooks> library = userBooksService.findByUserId(id);
        return ResponseEntity.ok(library.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst().orElse(null));
    }
    @PostMapping("me/library/")
    public ResponseEntity<UserBooks> addBookToLibrary(@AuthenticationPrincipal UserInfoDetails user,
                                           @RequestBody @Valid AddUserBookDTO request) {
        UserBooks addedBook = userBooksService.addToLibrary(user.getId(), request);
        return null;
    };

}
