package com.example.assignmentsubmissionapp.service.impl;

import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.repository.AuthorityRepository;
import com.example.assignmentsubmissionapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    final private UserRepository userRepo;

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}
