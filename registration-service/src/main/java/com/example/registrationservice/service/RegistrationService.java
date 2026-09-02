package com.example.registrationservice.service;

import com.example.registrationservice.client.CourseClient;
import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.entity.Registration;
import com.example.registrationservice.entity.RegistrationStatus;
import com.example.registrationservice.repository.RegistrationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final CourseClient courseClient;

    public RegistrationDTO register(
            Long studentId,
            Long courseId
    ) {

        // Kiểm tra sinh viên đã đăng ký môn này chưa
        boolean exists =
                registrationRepository
                        .existsByStudentIdAndCourseIdAndStatus(
                                studentId,
                                courseId,
                                RegistrationStatus.REGISTERED
                        );

        if (exists) {
            throw new IllegalStateException(
                    "Sinh vien da dang ky mon hoc nay roi"
            );
        }

        // Gọi course-service để giữ chỗ
        courseClient.reserveSeat(courseId);

        // Lưu đăng ký
        Registration registration = new Registration();

        registration.setStudentId(studentId);
        registration.setCourseId(courseId);
        registration.setStatus(
                RegistrationStatus.REGISTERED
        );

        return toDTO(
                registrationRepository.save(registration)
        );
    }

    public List<RegistrationDTO> getByStudent(
            Long studentId
    ) {

        return registrationRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RegistrationDTO cancel(
            Long id
    ) {

        Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "Khong tim thay dang ky id = " + id
                                )
                        );

        if (registration.getStatus()
                == RegistrationStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Dang ky nay da bi huy"
            );
        }

        // Trả lại chỗ cho môn học
        courseClient.releaseSeat(
                registration.getCourseId()
        );

        // Chuyển trạng thái thành đã hủy
        registration.setStatus(
                RegistrationStatus.CANCELLED
        );

        return toDTO(
                registrationRepository.save(registration)
        );
    }

    private RegistrationDTO toDTO(
            Registration registration
    ) {

        return new RegistrationDTO(
                registration.getId(),
                registration.getStudentId(),
                registration.getCourseId(),
                registration.getStatus().name(),
                registration.getCreatedAt()
        );
    }

    public List<Registration> getMyRegistrations(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }
}