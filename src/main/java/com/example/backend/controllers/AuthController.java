package com.example.backend.controllers;

import com.example.backend.DTO.*;
import com.example.backend.models.*;
import com.example.backend.repositories.UserInfoRepository;
import com.example.backend.services.JwtService;
import com.example.backend.services.RefreshTokenService;
import com.example.backend.services.UserInfoService;
import exceptions.TokenRefreshException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserInfoService userInfoService, UserInfoRepository userInfoRepository,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          RefreshTokenService refreshTokenService) {
        this.userInfoService = userInfoService;
        this.userInfoRepository = userInfoRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        Optional<UserInfo> existingUser = userInfoService.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Conflict: User with username already exists");
        }

        UserInfo createdUser = userInfoService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), roles));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(new UserInfoDetails(user));
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.ok(userInfoRepository.findAll());
    }

}