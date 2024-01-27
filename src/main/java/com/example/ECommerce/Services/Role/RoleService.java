package com.example.ECommerce.Services.Role;


import com.example.ECommerce.DAOs.Role.Role;

public interface RoleService {

    public Role fetchRoleByName(final String roleName);
}
