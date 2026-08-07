package com.example.courseservice.service;

import com.example.courseservice.dto.CourseDTO;
import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
    }

    @Test
    void testCreateCourseSuccess() {
        CourseDTO dto = new CourseDTO(null, "Lap trinh Java", 3, 40, null);
        CourseDTO created = courseService.create(dto);

        assertNotNull(created.getId());
        assertEquals("Lap trinh Java", created.getTenMonHoc());
        assertEquals(40, created.getSoChoToiDa());
        assertEquals(40, created.getSoChoConLai());
    }

    @Test
    void testCreateCourseDuplicateNameThrowsException() {
        CourseDTO dto1 = new CourseDTO(null, "Lap trinh Java", 3, 40, null);
        courseService.create(dto1);

        CourseDTO dto2 = new CourseDTO(null, "LAP TRINH JAVA", 3, 30, null);
        assertThrows(IllegalArgumentException.class, () -> courseService.create(dto2));
    }

    @Test
    void testUpdateCourseDuplicateNameThrowsException() {
        CourseDTO c1 = courseService.create(new CourseDTO(null, "Mon A", 3, 40, null));
        CourseDTO c2 = courseService.create(new CourseDTO(null, "Mon B", 3, 40, null));

        CourseDTO updateDto = new CourseDTO(c2.getId(), "MON A", 3, 40, null);
        assertThrows(IllegalArgumentException.class, () -> courseService.update(c2.getId(), updateDto));
    }

    @Test
    void testUpdateCourseSeatCountAdjustsRemainingSeats() {
        CourseDTO created = courseService.create(new CourseDTO(null, "Mon A", 3, 50, null));

        // Simulate 10 students registered (remaining = 40)
        Course entity = courseRepository.findById(created.getId()).orElseThrow();
        entity.setSoChoConLai(40);
        courseRepository.save(entity);

        // Update max seats to 60 -> remaining seats should become 50 (60 - 10 enrolled)
        CourseDTO updateDto = new CourseDTO(created.getId(), "Mon A", 3, 60, null);
        CourseDTO updated = courseService.update(created.getId(), updateDto);

        assertEquals(60, updated.getSoChoToiDa());
        assertEquals(50, updated.getSoChoConLai());
    }
}
