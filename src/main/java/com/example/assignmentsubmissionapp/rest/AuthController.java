package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.JwtRequest;
import com.example.assignmentsubmissionapp.dto.JwtResponse;
import com.example.assignmentsubmissionapp.service.impl.UserDetailsServiceImpl;
import com.example.assignmentsubmissionapp.util.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    final private JWTUtility jwtUtility;

    final private AuthenticationManager authenticationManager;

    final private UserDetailsServiceImpl userService;

    @PostMapping("/auth")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e){
            throw new Exception("Invalid credentials", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token
                = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
