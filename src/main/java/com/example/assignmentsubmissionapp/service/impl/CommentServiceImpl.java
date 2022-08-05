package com.example.assignmentsubmissionapp.service.impl;

import com.example.assignmentsubmissionapp.dto.CommentRequest;
import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.Comment;
import com.example.assignmentsubmissionapp.entity.User;
import com.example.assignmentsubmissionapp.repository.AssignmentRepository;
import com.example.assignmentsubmissionapp.repository.CommentRepository;
import com.example.assignmentsubmissionapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final private AssignmentRepository assignmentRepo;

    final private CommentRepository commentRepository;

    @Override
    public Comment save(CommentRequest commentRequest, User user) {
        Assignment assignment = assignmentRepo.getReferenceById(commentRequest.getAssignmentId());

        Comment.CommentBuilder commentBuilder = Comment.builder();
        commentBuilder
                .id(commentRequest.getId())
                .commentText(commentRequest.getCommentText())
                .assignment(assignment)
                .createdBy(user);

                if(commentRequest.getId() == null) {
                commentBuilder.createdDate(ZonedDateTime.now());
                }

        Comment comment = commentBuilder.build();
        System.out.println(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Set<Comment> getCommentsByAssignmentId(Long assignmentId) {
        Set<Comment> comments = commentRepository.findByAssignmentId(assignmentId);
        System.out.println(comments);
        return comments;
    }
}