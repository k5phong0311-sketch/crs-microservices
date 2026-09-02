package com.example.registrationservice.controller;

import com.example.registrationservice.dto.RegistrationDTO;
import com.example.registrationservice.service.RegistrationService;
import org.springframework.security.core.Authentication;
import com.example.registrationservice.entity.Registration;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationDTO> register(
            @RequestBody RegistrationDTO registrationDTO
    ) {

        RegistrationDTO result =
                registrationService.register(
                        registrationDTO.getStudentId(),
                        registrationDTO.getCourseId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RegistrationDTO>> getByStudent(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                registrationService.getByStudent(studentId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegistrationDTO> cancel(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                registrationService.cancel(id)
        );
    }

    @GetMapping("/my")
    public List<Registration> getMyRegistrations(Authentication authentication) {
        Long studentId = (Long) authentication.getCredentials();
        return registrationService.getMyRegistrations(studentId);
    }
}