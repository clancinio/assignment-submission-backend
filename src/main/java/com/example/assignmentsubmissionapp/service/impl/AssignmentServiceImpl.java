package com.example.assignmentsubmissionapp.service.impl;

import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.enums.AssignmentStatusEnum;
import com.example.assignmentsubmissionapp.enums.AuthorityEnum;
import com.example.assignmentsubmissionapp.repository.AssignmentRepository;
import com.example.assignmentsubmissionapp.service.AssignmentService;
import com.example.assignmentsubmissionapp.util.AuthorityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    final private AssignmentRepository assignmentRepo;

    final private UserServiceImpl userService;

    @Override
    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setNumber(getNextAssignment(user));
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setUser(user);

        return assignmentRepo.save(assignment);
    }

    @Override
    public Assignment save(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    @Override
    public List<Assignment> getAssignments(User user) {
        boolean hasCodeReviewerRole = user.getAuthorities().stream().anyMatch(auth -> AuthorityEnum.ROLE_CODE_REVIEWER.name().equals(auth.getAuthority()));
        if (hasCodeReviewerRole) {
            // load assignments if you're a code reviewer role
            return assignmentRepo.findByCodeReviewer(user);
        } else {
            // load assignments if you're a student role
            return assignmentRepo.findByUser(user);
        }
    }

    @Override
    public Optional<Assignment> getAssignmentById(Long assignmentId) {
        return assignmentRepo.findById(assignmentId);
    }

    @Override
    public Assignment assignAssignment(Assignment assignment, User user) {
        assignment.setCodeReviewer(user);
        assignment.setStatus(AssignmentStatusEnum.IN_REVIEW.getStatus());
        return assignmentRepo.save(assignment);
    }

    private Integer getNextAssignment(User user) {
        List<Assignment> assignmentsByUser = assignmentRepo.findByUser(user);
        if (assignmentsByUser == null) {
            return 1;
        }
        Optional<Integer> nextAssignmentNumOpt = assignmentsByUser.stream().sorted((a1, a2) -> {
            if (a1.getNumber() == null)
                return 1;
            if (a2.getNumber() == null)
                return 1;
            return a2.getNumber().compareTo(a1.getNumber());
        }).map(assignment -> {
            if (assignment.getNumber() == null)
                return 1;
            return assignment.getNumber() + 1;
        }).findFirst();
        return nextAssignmentNumOpt.orElse(1);
    }
}
