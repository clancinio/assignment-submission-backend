package com.example.assignmentsubmissionapp.service;

import com.example.assignmentsubmissionapp.dto.CommentRequest;
import com.example.assignmentsubmissionapp.entity.Comment;
import com.example.assignmentsubmissionapp.entity.User;

import java.util.List;
import java.util.Set;

public interface CommentService {

    Comment save(CommentRequest comment, User user);

    Set<Comment> getCommentsByAssignmentId(Long assignmentId);
}
