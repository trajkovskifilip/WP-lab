package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.CourseType;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCourseRepository {

    public static List<Course> courses = new ArrayList<>();

    @PostConstruct
    public void init() {
        courses.add(new Course("Veb programiranje", "VP", CourseType.WINTER));
        courses.add(new Course("Strukturno programiranje", "SP", CourseType.WINTER));
        courses.add(new Course("Kompjuterski mrezi", "KM", CourseType.WINTER));
        courses.add(new Course("Operativni sitemi", "OS", CourseType.SUMMER));
        courses.add(new Course("Softversko inzenerstvo", "SI", CourseType.SUMMER));
    }

    public List<Course> findAllCourses() {
        return courses.stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList());
    }

    public Optional<Course> findById(Long courseId) {
        return courses.stream().filter(c -> c.getCourseId().equals(courseId)).findFirst();
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
            return findById(courseId).get().getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
        course.getStudents().add(student);
        return course;
    }

    public void deleteById(Long id) {
        courses.removeIf(i -> i.getCourseId().equals(id));
    }

    public List<Course> findByType(CourseType type) {
        return courses.stream().filter(i -> i.getType().equals(type)).collect(Collectors.toList());
    }

    public Optional<Course> save(String name, String description, Teacher teacher, CourseType type) {
        courses.removeIf(i -> i.getName().equals(name));
        Course course = new Course(name, description, teacher, type);
        courses.add(course);
        return Optional.of(course);
    }
}
