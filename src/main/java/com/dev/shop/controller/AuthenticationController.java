package com.dev.shop.controller;

import com.dev.shop.dto.AuthenticationLoginRequestDto;
import com.dev.shop.dto.AuthenticationRegisterRequestDto;
import com.dev.shop.model.Role;
import com.dev.shop.model.User;
import com.dev.shop.security.jwt.JwtTokenProvider;
import com.dev.shop.service.AuthenticationService;
import com.dev.shop.service.RoleService;
import com.dev.shop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private final Logger logger = LogManager.getLogger(AuthenticationController.class);
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private AuthenticationService authenticationService;
    private RoleService roleService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    UserService userService,
                                    AuthenticationService authenticationService,
                                    RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        for (Role.RoleName roleName : Role.RoleName.values()) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleService.create(role);
        }
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody AuthenticationLoginRequestDto dto) {
        try {
            String username = dto.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
            User user = userService.findByUsername(username);
            if (user == null) {
                logger.info("User with such username: " + username + " was not found!");
                throw new UsernameNotFoundException("User not found!");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.info("Invalid credentials!");
            throw new BadCredentialsException("Invalid credentials! "
                    + "Check the username and password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthenticationRegisterRequestDto
                                           dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try {
            String jsonData = objectMapper.writeValueAsString(dto);
            user = objectMapper.readValue(jsonData, User.class);
        } catch (JsonProcessingException e) {
            logger.info("There is an error while creating User!");
            throw new RuntimeException("There is an error while creating User! "
                    + "Check if you entered valid data!");
        }
        authenticationService.register(user);
        Map<Object, Object> response = new HashMap<>();
        response.put("message", "You have registered successfully");
        return ResponseEntity.ok(response);
    }
}
