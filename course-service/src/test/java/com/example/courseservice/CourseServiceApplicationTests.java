package com.example.courseservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD

@SpringBootTest
=======
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
>>>>>>> 3350210 (feat(course-service): CRUD course 3-layer + DTO)
class CourseServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
