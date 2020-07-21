package com.dev.shop.service.impl;

import com.dev.shop.model.Role;
import com.dev.shop.model.Status;
import com.dev.shop.model.User;
import com.dev.shop.service.AuthenticationService;
import com.dev.shop.service.RoleService;
import com.dev.shop.service.UserService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, RoleService roleService,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> roles.add(role));
        }
        roles.add(roleService.findByRoleName(Role.RoleName.USER));
        user.setRoles(roles);
        return userService.create(user);
    }
}
