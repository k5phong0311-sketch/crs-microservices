
package com.example.courseservice.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
        import lombok.AllArgsConstructor;
=======
import lombok.AllArgsConstructor;
>>>>>>> 3350210 (feat(course-service): CRUD course 3-layer + DTO)
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mon_hoc", nullable = false, length = 255)
    private String tenMonHoc;

    @Column(name = "so_tin_chi", nullable = false)
    private Integer soTinChi;

    @Column(name = "so_cho_toi_da", nullable = false)
    private Integer soChoToiDa;

    @Column(name = "so_cho_con_lai", nullable = false)
    private Integer soChoConLai;
<<<<<<< HEAD
=======

    @Version
    private Long version;
>>>>>>> 3350210 (feat(course-service): CRUD course 3-layer + DTO)
}