package com.example.assignmentsubmissionapp.util;

import com.example.assignmentsubmissionapp.entity.User;

public class AuthorityUtil {
    public static Boolean hasRole(String role, User user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
