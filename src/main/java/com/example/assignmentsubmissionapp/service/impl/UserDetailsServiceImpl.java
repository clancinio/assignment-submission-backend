package com.example.assignmentsubmissionapp.service.impl;

import com.example.assignmentsubmissionapp.dto.RegisterRequest;
import com.example.assignmentsubmissionapp.dto.RegistrationResponse;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final private UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
    }

    public RegistrationResponse saveUser(RegisterRequest request) {
        try {
            final User userEntity = objectMapper.convertValue(request, User.class);
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new RuntimeException("User with the user name " + request.getUsername() + " already exists");
            } else {
                userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                User user = userRepository.save(userEntity);
                return new RegistrationResponse(user.getUsername());
            }
        } catch (final Exception exception) {
            log.error(exception);
            throw new RuntimeException("Something went wrong!", exception);
        }
    }
}