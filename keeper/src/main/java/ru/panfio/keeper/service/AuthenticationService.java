package ru.panfio.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panfio.keeper.domain.Role;
import ru.panfio.keeper.domain.RoleName;
import ru.panfio.keeper.domain.User;
import ru.panfio.keeper.domain.payload.SignUpRequest;
import ru.panfio.keeper.exception.AppException;
import ru.panfio.keeper.repository.RoleRepo;
import ru.panfio.keeper.repository.UserRepo;

import java.net.URI;
import java.util.Collections;

@Slf4j
@Service
public class AuthenticationService {


    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepo userRepository, RoleRepo roleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public URI creareUserAccount(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

    }
}
