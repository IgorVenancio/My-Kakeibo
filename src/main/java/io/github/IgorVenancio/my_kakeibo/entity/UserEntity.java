package io.github.IgorVenancio.my_kakeibo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String passwordHash;
    private String passwordSalt;
    private String currencyPreference;
    @Column(updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    private boolean isActive;
    private String activationToken;
    private OffsetDateTime activationTokenExpiry;

    @PrePersist
    public void prePersist() {
        if (userId == null) {
            userId = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
        updatedAt = OffsetDateTime.now();
        if (activationToken == null) {
            activationToken = UUID.randomUUID().toString();
            activationTokenExpiry = OffsetDateTime.now().plusHours(12);
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
