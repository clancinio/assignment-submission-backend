package com.example.assignmentsubmissionapp.repository;

import com.example.assignmentsubmissionapp.entity.Assignment;
import com.example.assignmentsubmissionapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByUser(User user);
}
