package com.example.courseservice.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @GetMapping
    public List<Map<String, Object>> getMockCourses() {

        return List.of(
                Map.of(
                        "id", 1,
                        "tenMonHoc", "Lap trinh Java co ban",
                        "soTinChi", 3,
                        "soChoToiDa", 40,
                        "soChoConLai", 12
                ),
                Map.of(
                        "id", 2,
                        "tenMonHoc", "Co so du lieu",
                        "soTinChi", 4,
                        "soChoToiDa", 35,
                        "soChoConLai", 0
                )
        );
=======
import com.example.courseservice.dto.CourseDTO;
import com.example.courseservice.service.CourseService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAll() {

        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public CourseDTO getById(@PathVariable Long id) {

        return courseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO create(
            @Valid @RequestBody CourseDTO dto
    ) {

        return courseService.create(dto);
    }

    @PutMapping("/{id}")
    public CourseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO dto
    ) {

        return courseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        courseService.delete(id);
>>>>>>> 3350210 (feat(course-service): CRUD course 3-layer + DTO)
    }
}