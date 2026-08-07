package com.example.courseservice.service;

import com.example.courseservice.dto.CourseDTO;
import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAll() {

        return courseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Khong tim thay mon hoc id = " + id
                        )
                );

        return toDTO(course);
    }

    public CourseDTO create(CourseDTO dto) {

        if (courseRepository.existsByTenMonHocIgnoreCase(
                dto.getTenMonHoc()
        )) {

            throw new IllegalArgumentException(
                    "Ten mon hoc da ton tai"
            );
        }

        Course course = new Course();

        course.setTenMonHoc(dto.getTenMonHoc());
        course.setSoTinChi(dto.getSoTinChi());
        course.setSoChoToiDa(dto.getSoChoToiDa());

        // Khi tao moi:
        // soChoConLai = soChoToiDa
        course.setSoChoConLai(dto.getSoChoToiDa());

        return toDTO(
                courseRepository.save(course)
        );
    }

    public CourseDTO update(Long id, CourseDTO dto) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Khong tim thay mon hoc id = " + id
                        )
                );

        if (courseRepository.existsByTenMonHocIgnoreCaseAndIdNot(dto.getTenMonHoc(), id)) {
            throw new IllegalArgumentException("Ten mon hoc da ton tai");
        }

        int enrolled = course.getSoChoToiDa() - course.getSoChoConLai();
        if (dto.getSoChoToiDa() < enrolled) {
            throw new IllegalArgumentException(
                    "So cho toi da moi khong duoc nho hon so cho da dang ky (" + enrolled + ")"
            );
        }

        course.setTenMonHoc(dto.getTenMonHoc());
        course.setSoTinChi(dto.getSoTinChi());
        course.setSoChoToiDa(dto.getSoChoToiDa());
        course.setSoChoConLai(dto.getSoChoToiDa() - enrolled);

        return toDTO(
                courseRepository.save(course)
        );
    }

    public void delete(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Khong tim thay mon hoc id = " + id
                        )
                );

        if (course.getSoChoConLai() < course.getSoChoToiDa()) {
            throw new IllegalArgumentException("Khong the xoa mon hoc da co sinh vien dang ky");
        }

        courseRepository.deleteById(id);
    }

    private CourseDTO toDTO(Course course) {

        return new CourseDTO(
                course.getId(),
                course.getTenMonHoc(),
                course.getSoTinChi(),
                course.getSoChoToiDa(),
                course.getSoChoConLai()
        );
    }
}