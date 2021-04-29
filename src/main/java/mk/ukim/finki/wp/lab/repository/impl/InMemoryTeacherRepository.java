package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTeacherRepository {
    public static List<Teacher> teachers = new ArrayList<>();

    @PostConstruct
    public void init() {
        teachers.add(new Teacher("Dimitar", "Trajanov"));
        teachers.add(new Teacher("Riste", "Stojanov"));
        teachers.add(new Teacher("Sonja", "Gievska"));
        teachers.add(new Teacher("Kostadin", "Mishev"));
        teachers.add(new Teacher("Ana", "Todorovska"));
    }

    public List<Teacher> findAll() {
        return teachers;
    }

    public Optional<Teacher> findById(Long id) {
        return teachers.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
