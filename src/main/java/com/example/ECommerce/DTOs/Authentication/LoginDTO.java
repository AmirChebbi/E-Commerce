package com.example.ECommerce.DTOs.Authentication;


import org.intellij.lang.annotations.Pattern;

public class LoginDTO {
    private String email;
    private String password;


    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
