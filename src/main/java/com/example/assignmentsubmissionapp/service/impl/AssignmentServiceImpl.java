package com.example.assignmentsubmissionapp.service.impl;

import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.repository.AssignmentRepository;
import com.example.assignmentsubmissionapp.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    final private AssignmentRepository assignmentRepo;

    @Override
    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus("Not submitted");
        assignment.setUser(user);

        return assignmentRepo.save(assignment);
    }

    @Override
    public List<Assignment> getAssignments(User user) {
        return assignmentRepo.findByUser(user);
    }

    @Override
    public Optional<Assignment> getAssignmentById(Long assignmentId) {
        return assignmentRepo.findById(assignmentId);
    }
}
