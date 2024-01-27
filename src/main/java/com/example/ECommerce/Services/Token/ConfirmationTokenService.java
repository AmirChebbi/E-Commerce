package com.example.ECommerce.Services.Token;



import com.example.ECommerce.DAOs.Token.ConfirmationToken;
import com.example.ECommerce.DAOs.User.UserEntity;
import org.jetbrains.annotations.NotNull;

public interface ConfirmationTokenService {


    public ConfirmationToken fetchTokenByToken(final String token);
    public String generateConfirmationToken(@NotNull UserEntity userEntity);
    public void setConfirmedAt(final String token);
    public void saveConfirmationToken(@NotNull ConfirmationToken confirmationToken);

    public String getConfirmationPage();
    public String getAlreadyConfirmedPage();

}
