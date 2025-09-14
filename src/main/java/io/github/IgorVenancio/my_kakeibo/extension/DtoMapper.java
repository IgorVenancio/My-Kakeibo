package io.github.IgorVenancio.my_kakeibo.extension;

import io.github.IgorVenancio.my_kakeibo.dto.UserDto;
import io.github.IgorVenancio.my_kakeibo.entity.UserEntity;

import java.time.OffsetDateTime;

public class DtoMapper {

    // Convert from UserDTO to UserEntity
    public static UserEntity toEntity(UserDto userDto) {
        UserEntity.UserEntityBuilder builder = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .passwordHash(userDto.getPasswordHash())
                .passwordSalt(userDto.getPasswordSalt())
                .currencyPreference(userDto.getCurrencyPreference());

        // Optional fields
        if (userDto.getUserId() != null) {
            builder.userId(userDto.getUserId());
        }
        if (userDto.getCreatedAt() != null) {
            builder.createdAt(userDto.getCreatedAt());
        }

        return builder.build();
    }

    // Convert from UserEntity to UserDTO
    public static UserDto toDto(UserEntity userEntity) {
        UserDto.UserDtoBuilder builder = UserDto.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .currencyPreference(userEntity.getCurrencyPreference())
                .createdAt(userEntity.getCreatedAt());

        return builder.build();
    }
}
