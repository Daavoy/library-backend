package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    // EAGER fetch because roles are needed immediately during authentication
    // (Spring Security loads UserDetails synchronously) — lazy loading here
    // would risk a LazyInitializationException outside a transaction
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING) // store "ROLE_USER" not the ordinal (0, 1, ...)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();
    public UserInfo(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserInfo() {
    }
}
