package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.*;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.service.impl.UserDetailsServiceImpl;
import com.example.assignmentsubmissionapp.util.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    final private JWTUtility jwtUtility;

    final private AuthenticationManager authenticationManager;

    final private UserDetailsServiceImpl userService;

    @GetMapping("/")
    public String home() {
        return "Welcome home!!";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token
                = jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity(userService.saveUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token,
                                           @AuthenticationPrincipal User user) {
        try {
            Boolean isValidToken = jwtUtility.validateToken(token, user);
            System.out.println("Is valid" + isValidToken);
            ValidateResponse res = new ValidateResponse();
            res.setIsValid(isValidToken);
            return ResponseEntity.ok(res);
        } catch (ExpiredJwtException e) {
            ValidateResponse res = new ValidateResponse();
            res.setIsValid(false);
            System.out.println("Is valid" + res);
            return ResponseEntity.ok(res);
        }
    }
}