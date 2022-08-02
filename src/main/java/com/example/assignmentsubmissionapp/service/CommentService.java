package com.example.assignmentsubmissionapp.service;

import com.example.assignmentsubmissionapp.dto.CommentRequest;
import com.example.assignmentsubmissionapp.entity.Comment;
import com.example.assignmentsubmissionapp.entity.User;

public interface CommentService {

    Comment save(CommentRequest comment, User user);

}
