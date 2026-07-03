package com.example.backend.DTO;

import com.example.backend.models.Role;
import com.example.backend.models.UserInfo;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String userName;
    private Set<Role> roles;


    public UserResponse(UserInfo user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
    }
}
