package io.github.IgorVenancio.my_kakeibo.user.application;

import io.github.IgorVenancio.my_kakeibo.user.api.UserDto;
import io.github.IgorVenancio.my_kakeibo.common.infrastructure.EmailService;
import io.github.IgorVenancio.my_kakeibo.user.domain.ActivationResult;
import io.github.IgorVenancio.my_kakeibo.user.domain.UserEntity;
import io.github.IgorVenancio.my_kakeibo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserDto userDto) {
        // Convert from DTO, insert new entity, and return result as DTO
        UserEntity userEntity = UserMapper.toEntity(userDto);
        userEntity.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
        userEntity = userRepository.save(userEntity);

        // Send email for account activation
        SendAccountActivationMail(userEntity);

        return UserMapper.toDto(userEntity);
    }

    /*
        TODO
        Improve this method. Maybe creating a template with the formatted email.
     */
    private void SendAccountActivationMail(UserEntity user) {

        String activationLink = "http://127.0.0.1:8080/api/v1.0/activate?token=" + user.getActivationToken();
        String subject = "Activate your My Kakeibo account.";
        String body = "Click on the following link to activate your account: " + activationLink;
        emailService.SendMail(user.getEmail(), subject, body);
    }

    /* Search user by activation token.
        If no results, return INVALID_TOKEN.
        If it finds a user, but the token expiry timestamp is null or before than now, return EXPIRED_TOKEN.
        If it passes the conditions, activate the user and set token and expiry timestamp as null. Return SUCCESS.
     */
    public ActivationResult activateUser(String activationToken) {
        return userRepository.findByActivationToken(activationToken)
                .map(user -> {
                    if (user.getActivationTokenExpiry() == null
                            || user.getActivationTokenExpiry().isBefore(OffsetDateTime.now())){
                        return ActivationResult.EXPIRED_TOKEN;
                    }

                    // Nullify the token to avoid reuse of activation token.
                    user.setActivationToken(null);
                    user.setActivationTokenExpiry(null);
                    user.setActive(true);

                    userRepository.save(user);
                    return ActivationResult.SUCCESS;
                })
                .orElse(ActivationResult.INVALID_TOKEN);
    }
}
