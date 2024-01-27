package com.example.ECommerce.Services.Authentication;


import com.example.ECommerce.DTOs.Authentication.LoginDTO;
import com.example.ECommerce.DTOs.Authentication.RegisterDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<Object> register(@NotNull final RegisterDTO registerDto) ;
    public ResponseEntity<Object>  login(@NotNull final LoginDTO loginDto);
    public ResponseEntity<Object> renewAccessToken(final String refreshToken, final String expiredToken);
    public String confirmToken(final String token);

}
