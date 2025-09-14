package io.github.IgorVenancio.my_kakeibo.service;

import io.github.IgorVenancio.my_kakeibo.dto.UserDto;
import io.github.IgorVenancio.my_kakeibo.entity.UserEntity;
import io.github.IgorVenancio.my_kakeibo.extension.DtoMapper;
import io.github.IgorVenancio.my_kakeibo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserDto registerUser(UserDto userDto) {
        // Convert from DTO, insert new entity, and return result as DTO
        UserEntity userEntity = DtoMapper.toEntity(userDto);
        userEntity = userRepository.save(userEntity);

        // Send email for account activation
        String activationLink = "http://127.0.0.1:8080/api/v1.0/activate?token=" + userEntity.getActivationToken();
        String subject = "Activate your My Kakeibo account.";
        String body = "Click on the following link to activate your account: " + activationLink;
        emailService.SendMail(userEntity.getEmail(), subject, body);

        return DtoMapper.toDto(userEntity);
    }
}
