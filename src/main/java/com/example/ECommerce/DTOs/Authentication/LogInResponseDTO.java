package com.example.ECommerce.DTOs.Authentication;

import com.example.ECommerce.DTOs.User.UserEntityDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInResponseDTO {
    private UserEntityDTO userEntityDTO;
    private String accessToken;
    private String refreshToken;
}
