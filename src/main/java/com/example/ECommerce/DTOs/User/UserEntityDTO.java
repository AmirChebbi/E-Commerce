package com.example.ECommerce.DTOs.User;


import com.example.ECommerce.DAOs.Role.Role;

import java.util.Date;
import java.util.UUID;

public record UserEntityDTO (
        UUID id,
        String firstName,
        String lastName,
        String email,
        String address,
        String company,
        String phoneNumber,
        Date creationDate,
        boolean isEnabled,
        Role role
){
}
