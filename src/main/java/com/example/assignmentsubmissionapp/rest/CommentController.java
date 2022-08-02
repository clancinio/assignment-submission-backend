package com.example.assignmentsubmissionapp.rest;

import com.example.assignmentsubmissionapp.dto.CommentRequest;
import com.example.assignmentsubmissionapp.entity.Comment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.service.impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
