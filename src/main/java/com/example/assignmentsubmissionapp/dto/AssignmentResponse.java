package com.example.assignmentsubmissionapp.dto;

import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.enums.AssignmentEnum;
import com.example.assignmentsubmissionapp.enums.AssignmentStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AssignmentResponse {

    private Assignment assignment;
    private List<AssignmentEnum> assignmentEnumList = List.of(AssignmentEnum.values());
    private List<AssignmentStatusEnum> assignmentStatusEnumList = List.of(AssignmentStatusEnum.values());
}
