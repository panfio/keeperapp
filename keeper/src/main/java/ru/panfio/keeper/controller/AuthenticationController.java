package ru.panfio.keeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.panfio.keeper.domain.payload.ApiResponse;
import ru.panfio.keeper.domain.payload.JwtAuthenticationResponse;
import ru.panfio.keeper.domain.payload.LoginRequest;
import ru.panfio.keeper.domain.payload.SignUpRequest;
import ru.panfio.keeper.repository.UserRepo;
import ru.panfio.keeper.service.AuthenticationService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepo userRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    UserRepo userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse response = authenticationService.authUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(
                    new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        URI userLocation = authenticationService.creareUserAccount(signUpRequest);
        return ResponseEntity.created(userLocation).body(new ApiResponse(true, "User registered successfully"));
    }
}
