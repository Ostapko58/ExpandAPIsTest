package com.onyshkiv.expandapistest.controller;

import com.onyshkiv.expandapistest.entity.AuthenticationResponseDto;
import com.onyshkiv.expandapistest.entity.User;
import com.onyshkiv.expandapistest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/add")
    public void addNewUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticateUser(@RequestBody User user) {
        String token = userService.authenticate(user);
        AuthenticationResponseDto loginRes = new AuthenticationResponseDto(token);
        return ResponseEntity.ok(loginRes);
    }

}
