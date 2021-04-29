package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.CourseType;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String text,
                                 Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if (type != null) {
            CourseType courseType = CourseType.valueOf(type);
            model.addAttribute("courses", this.courseService.listByType(courseType));
        }
        else if (text != null && !text.isEmpty()) {
            model.addAttribute("courses", this.courseService.searchByNameOrDescription(text));
        }
        else {
            model.addAttribute("courses", this.courseService.listAll());
        }
        model.addAttribute("bodyContent", "listCourses");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) Long id,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long teacherId,
                             @RequestParam String type) {
        CourseType courseType = CourseType.valueOf(type);
        if (id != null) {
            this.courseService.edit(id, name, description, teacherId, courseType);
        }
        else {
            this.courseService.save(name, description, teacherId, courseType);
        }
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCoursePage(Model model) {
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("bodyContent", "add-course");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCoursePage(@PathVariable Long id, Model model) {
        if (this.courseService.searchById(id).isPresent()) {
            Course course = this.courseService.searchById(id).get();
            List<Teacher> teachers = this.teacherService.findAll();
            model.addAttribute("course", course);
            model.addAttribute("teachers", teachers);
            model.addAttribute("bodyContent", "add-course");
            return "master-template";
        }
        return "redirect:/courses?error=CourseNotFound";
    }
}
