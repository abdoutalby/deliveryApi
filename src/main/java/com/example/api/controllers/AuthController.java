package com.example.api.controllers;


import com.example.api.configuration.JwtProvider;
import com.example.api.models.Role;
import com.example.api.models.RoleName;
import com.example.api.models.User;
import com.example.api.repos.RoleRepository;
import com.example.api.repos.UserRepo;
import com.example.api.services.EmailSenderService;
import com.example.api.utils.JwtResponse;
import com.example.api.utils.LoginForm;
import com.example.api.utils.RegisterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@Transactional
@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtProvider jwtProvider;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        log.info("email : {} and password : {}", loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()

                )
        );
        log.info("email : {} and password : {}", loginRequest.getEmail(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account

        Set<Role> roles = new HashSet<>();
        switch (signUpRequest.getRole()) {
            case "user": {
                Optional<Role> r = roleRepository.findByName(RoleName.ROLE_USER);
                if (r.isPresent()) {
                    roles.add(r.get());
                } else {
                    return ResponseEntity.badRequest().body("role not found");
                }
                break;
            }
            case "admin": {
                Optional<Role> r = roleRepository.findByName(RoleName.ROLE_ADMIN);
                if (r.isPresent()) {
                    roles.add(r.get());
                } else {
                    return ResponseEntity.badRequest().body("role not found");
                }
                break;
            }
        }

        User user = new User(null,
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getTel(),
                roles  );

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}