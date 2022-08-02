package com.example.assignmentsubmissionapp.repository;

import com.example.assignmentsubmissionapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
