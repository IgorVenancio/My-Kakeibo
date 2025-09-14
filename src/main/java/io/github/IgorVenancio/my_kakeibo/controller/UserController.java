package io.github.IgorVenancio.my_kakeibo.controller;

import io.github.IgorVenancio.my_kakeibo.dto.UserDto;
import io.github.IgorVenancio.my_kakeibo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = userService.registerUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body (registeredUser);
    }
}
