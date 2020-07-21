package com.dev.shop.security;

import com.dev.shop.model.User;
import com.dev.shop.security.jwt.JwtUser;
import com.dev.shop.security.jwt.JwtUserFactory;
import com.dev.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with such username not found!");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}