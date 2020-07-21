package com.dev.shop.service;

import com.dev.shop.model.User;

public interface UserService {
    User create(User user);

    User findByUsername(String username);
}
