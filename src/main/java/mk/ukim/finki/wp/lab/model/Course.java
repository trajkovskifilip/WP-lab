package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enumerations.CourseType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String name;

    @Column(length = 3000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    List<Grade> grades;

    @Enumerated(EnumType.STRING)
    private CourseType type;

    public Course() {
    }

    public Course(String name, String description, CourseType type) {
        this.name = name;
        this.description = description;
        this.students = new ArrayList<>();
        this.type = type;
    }

    public Course(String name, String description, Teacher teacher, CourseType type) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.type = type;
    }

    public Course(String name, String description, List<Student> students) {
        this.name = name;
        this.description = description;
        this.students = students;
    }
}
