package com.example.ECommerce.Services.Token;

import com.example.ECommerce.DAOs.Token.Token;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.TokenRepository;
import com.example.ECommerce.Security.jwt.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService{
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository, JWTService jwtService)
    {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }


    @Override
    public Token getTokenByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(
                ()-> new ResourceNotFoundException("The token u provided could not be found in our system")
        );
    }

    @Override
    public List<Token> fetchAllValidTokenByUserId(UUID userId) {
        return tokenRepository.fetchAllValidTokenByUserId(userId);
    }

    @Override
    public Token save(@NotNull Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepository.saveAll(tokens);
    }
}
