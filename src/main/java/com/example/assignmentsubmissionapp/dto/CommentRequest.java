package com.example.assignmentsubmissionapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long assignmentId;
    private String commentText;
    private String user;
}
