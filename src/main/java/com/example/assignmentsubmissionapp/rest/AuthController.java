package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.JwtRequest;
import com.example.assignmentsubmissionapp.dto.JwtResponse;
import com.example.assignmentsubmissionapp.dto.RegisterRequest;
import com.example.assignmentsubmissionapp.dto.RegistrationResponse;
import com.example.assignmentsubmissionapp.service.impl.UserDetailsServiceImpl;
import com.example.assignmentsubmissionapp.util.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    final private JWTUtility jwtUtility;

    final private AuthenticationManager authenticationManager;

    final private UserDetailsServiceImpl userService;

    @GetMapping("/")
    public String home(){
        return "Welcome home!!";
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
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

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity(userService.saveUser(request), HttpStatus.CREATED);
    }
}
