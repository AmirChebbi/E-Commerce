package com.example.ECommerce.DTOs.User;

import com.example.ECommerce.DAOs.User.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserEntityDTOMapper  implements Function<UserEntity, UserEntityDTO> {
    @Override
    public UserEntityDTO apply(UserEntity userEntity) {
        return new UserEntityDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getAddress(),
                userEntity.getCompany().getName(),
                userEntity.getPhoneNumber(),
                userEntity.getCreatingDate(),
                userEntity.isEnabled(),
                userEntity.getRole()
        );
    }
}
