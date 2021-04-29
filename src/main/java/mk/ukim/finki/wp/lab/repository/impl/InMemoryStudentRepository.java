package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.StudentNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    public static List<Student> students = new ArrayList<>();

    @PostConstruct
    public void init() {
        students.add(new Student("petko", "pet", "Petko", "Petkovski"));
        students.add(new Student("filip", "ft", "Filip", "Trajkovski"));
        students.add(new Student("jana", "jj", "Jana", "Janevska"));
        students.add(new Student("tome", "tt", "Tome", "Tomevski"));
        students.add(new Student("sara", "sara", "Sara", "Stankovska"));
    }

    public List<Student> findAllStudents() {
        return students;
    }

    public List<Student> findAllByNameOrSurname(String text) {
        return students.stream().filter(s -> s.getName().contains(text) || s.getSurname().contains(text)).collect(Collectors.toList());
    }

    public Optional<Student> findByUsername(String username) {
        return students.stream().filter(s -> s.getUsername().equals(username)).findFirst();
    }

    public Student save(Student student) {
        students.removeIf(s -> s.getUsername().equals(student.getUsername()));
        students.add(student);
        return student;
    }
}
