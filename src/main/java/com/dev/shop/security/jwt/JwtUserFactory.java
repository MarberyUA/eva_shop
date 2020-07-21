package com.dev.shop.security.jwt;

import com.dev.shop.model.Role;
import com.dev.shop.model.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getUsername(),
                user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), user.getStatus(), user.getUpdated(),
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"
                        + role.getRoleName().name()))
                .collect(Collectors.toList());
    }
}