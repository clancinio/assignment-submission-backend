package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.CommentRequest;
import com.example.assignmentsubmissionapp.entity.Comment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    final private CommentServiceImpl commentService;

    @PostMapping("")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest, @AuthenticationPrincipal User user) {
        Comment comment = commentService.save(commentRequest, user);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<Set<Comment>> getCommentsByAssignment(@PathVariable Long assignmentId) {
        System.out.println("AssignmentID: " + assignmentId);
        Set<Comment> comments = commentService.getCommentsByAssignmentId(assignmentId);

        return ResponseEntity.ok(comments);
    }
}
