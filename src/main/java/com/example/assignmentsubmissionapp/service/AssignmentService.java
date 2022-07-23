package com.example.assignmentsubmissionapp.service;

import com.example.assignmentsubmissionapp.dto.AssignmentResponse;
import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    Assignment save(User user);

    Assignment save(Assignment assignment);

    List<Assignment> getAssignments(User user);

    Optional<Assignment> getAssignmentById(Long assignmentId);

}
