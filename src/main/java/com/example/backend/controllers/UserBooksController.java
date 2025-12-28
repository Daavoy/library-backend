package com.example.backend.controllers;


import com.example.backend.models.UserBooks;
import com.example.backend.services.UserBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBooksController {
    @Autowired
    private UserBooksService userBooksService;

    @GetMapping()
    public ResponseEntity<List<UserBooks>> getAllUserBooks() {
        return ResponseEntity.ok(userBooksService.findAll());
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<UserBooks> getUserBooks(@PathVariable Long id) {
        return userBooksService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
