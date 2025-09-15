package io.github.IgorVenancio.my_kakeibo.user.application;

import io.github.IgorVenancio.my_kakeibo.security.JwtUtil;
import io.github.IgorVenancio.my_kakeibo.user.api.AuthDto;
import io.github.IgorVenancio.my_kakeibo.user.domain.UserEntity;
import io.github.IgorVenancio.my_kakeibo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Map<String, Object> login(AuthDto authDto) {
        try {
            // Authenticate credentials
            var token = new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
            authenticationManager.authenticate(token);

            // Load user info after successful authentication
            UserEntity user = userRepository.findByEmail(authDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));

            if (!user.isActive()) {
                throw new RuntimeException("Account not activated.");
            }

            // Generate JWT token
            String jwt = jwtUtil.generateToken(user.getEmail());

            // Return JWT Token and public user info
            return Map.of(
                    "token", jwt,
                    "user", UserMapper.toDto(user)
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new RuntimeException("Login failed. Check your credentials or activate your account.");
        }
    }
}