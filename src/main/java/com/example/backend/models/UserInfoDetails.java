package com.example.backend.models;
import com.example.backend.utils.UserUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Setter
@Getter
public class UserInfoDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();
        this.authorities = UserUtils.toAuthorities(userInfo.getRoles());
    }
}