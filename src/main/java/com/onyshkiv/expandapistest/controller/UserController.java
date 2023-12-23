package com.onyshkiv.expandapistest.controller;

import com.onyshkiv.expandapistest.entity.AuthenticationResponseDto;
import com.onyshkiv.expandapistest.entity.User;
import com.onyshkiv.expandapistest.service.UserService;
import com.onyshkiv.expandapistest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;//todo може це забрати
    private final JwtUtil jwtUtil;
    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/add")
    public void addNewUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticateUser(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            User authUser = (User) userService.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(authUser);
            AuthenticationResponseDto loginRes = new AuthenticationResponseDto(token);
            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e) {
           log.error(e.getMessage());
           throw new BadCredentialsException(e.getMessage());
        }

    }
    @PostMapping("/test")
    public String addNewUser(){
        return "123";
    }


}
