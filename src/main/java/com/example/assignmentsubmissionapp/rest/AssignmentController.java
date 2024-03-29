package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.AssignmentResponse;
import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
public class AssignmentController {

    final private AssignmentService assignmentService;

    final private UserDetailsService userService;

    @GetMapping("")
    public ResponseEntity<List<Assignment>> getAssignments(@AuthenticationPrincipal User user) {
        List<Assignment> assignments = assignmentService.getAssignments(user);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getAssignment(@PathVariable Long assignmentId) {
        Optional<Assignment> assignmentOpt = assignmentService.getAssignmentById(assignmentId);
        AssignmentResponse response = new AssignmentResponse();
        response.setAssignment(assignmentOpt.orElse(new Assignment()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long assignmentId, @RequestBody Assignment assignment) {
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @PutMapping("/claim")
    public ResponseEntity<Assignment> assignAssignment(
            @RequestBody Assignment assignment,
            @AuthenticationPrincipal User user) {
        Assignment updatedAssignment = assignmentService.assignAssignment(assignment, user);
        return ResponseEntity.ok(updatedAssignment);
    }

    @PostMapping("")
    public ResponseEntity<Assignment> createAssignment(@AuthenticationPrincipal User user) {
        Assignment newAssignment = assignmentService.save(user);
        return ResponseEntity.ok(newAssignment);
    }
}
