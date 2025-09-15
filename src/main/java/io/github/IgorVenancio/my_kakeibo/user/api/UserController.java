package io.github.IgorVenancio.my_kakeibo.user.api;

import io.github.IgorVenancio.my_kakeibo.user.application.AuthService;
import io.github.IgorVenancio.my_kakeibo.user.application.UserService;
import io.github.IgorVenancio.my_kakeibo.user.domain.ActivationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = userService.registerUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body (registeredUser);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String token) {
        ActivationResult result = userService.activateUser(token);

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok("Account activated successfully.");
            case EXPIRED_TOKEN -> ResponseEntity.status(HttpStatus.GONE)
                    .body("Activation link expired. Please request a new one.");
            case INVALID_TOKEN -> ResponseEntity.badRequest()
                    .body("Invalid activation link.");
        };
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto authDto) {
        try {
            Map<String, Object> response = authService.login(authDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test successful";
    }
}
