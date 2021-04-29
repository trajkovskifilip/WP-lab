package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.enumerations.CourseType;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> listAll();
    List<Course> listByType(CourseType type);
    List<Course> searchByNameOrDescription(String text);
    Optional<Course> searchById(Long courseId);
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId, Character grade);
    Optional<Course> save(String name, String description, Long teacherId, CourseType type);
    Optional<Course> edit(Long id, String name, String description, Long teacherId, CourseType type);
    void deleteById(Long id);
}
