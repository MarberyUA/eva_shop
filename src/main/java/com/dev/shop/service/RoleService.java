package com.dev.shop.service;

import com.dev.shop.model.Role;

public interface RoleService {
    Role create(Role role);

    Role findByRoleName(Role.RoleName roleName);
}
