package com.example.registrationservice.repository;

import com.example.registrationservice.entity.Registration;
import com.example.registrationservice.entity.RegistrationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long> {

    boolean existsByStudentIdAndCourseIdAndStatus(
            Long studentId,
            Long courseId,
            RegistrationStatus status
    );

    List<Registration> findByStudentId(
            Long studentId
    );
}