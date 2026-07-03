package com.example.backend.utils;

import com.example.backend.models.Role;
import com.example.backend.models.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserUtils {

    //private constructor - this class should never be instantiated
    private UserUtils() {}

    public static List<GrantedAuthority> toAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public static boolean hasRole(UserInfo user, Role role) {
        return user.getRoles().contains(role);
    }
}
