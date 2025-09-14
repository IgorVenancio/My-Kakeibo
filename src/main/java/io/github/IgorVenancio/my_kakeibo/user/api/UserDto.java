package io.github.IgorVenancio.my_kakeibo.user.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID userId;
    private String name;
    private String email;
    private String passwordHash;
    private String currencyPreference;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
