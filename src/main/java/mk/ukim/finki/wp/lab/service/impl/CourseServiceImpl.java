package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.CourseType;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public CourseServiceImpl(CourseRepository courseRepository,
                             GradeRepository gradeRepository,
                             StudentService studentService,
                             TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> listByType(CourseType type) {
        return courseRepository.findByType(type);
    }

    @Override
    public List<Course> searchByNameOrDescription(String text) {
        return courseRepository.findAllByNameContainingOrDescriptionContaining(text, text);
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        if (courseRepository.findById(courseId).isPresent()) {
            return courseRepository.findById(courseId).get().getStudents();
        }
        else {
            throw new CourseNotFoundException(courseId);
        }
    }

    @Override
    public Optional<Course> searchById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId, Character grade) {
        Course course = courseRepository.findById(courseId).get();
        Student student = this.studentService.searchByUsername(username);
        course.getStudents().add(student);
        gradeRepository.save(new Grade(grade, student, course));
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Optional<Course> save(String name, String description, Long teacherId, CourseType type) {
        Teacher teacher = this.teacherService.findById(teacherId);
        return Optional.of(courseRepository.save(new Course(name, description, teacher, type)));
    }

    @Override
    @Transactional
    public Optional<Course> edit(Long id, String name, String description, Long teacherId, CourseType type) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        course.setName(name);
        course.setDescription(description);
        Teacher teacher = this.teacherService.findById(teacherId);
        course.setTeacher(teacher);
        course.setType(type);
        return Optional.of(this.courseRepository.save(course));
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
