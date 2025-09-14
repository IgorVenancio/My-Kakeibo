package io.github.IgorVenancio.my_kakeibo.user.application;

import io.github.IgorVenancio.my_kakeibo.user.domain.UserEntity;
import io.github.IgorVenancio.my_kakeibo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public abstract class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPasswordHash())
                .authorities(Collections.emptyList())
                .build();
    }
}
