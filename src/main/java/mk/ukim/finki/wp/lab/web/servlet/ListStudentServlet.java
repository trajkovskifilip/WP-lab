package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ListStudentServlet", urlPatterns = "/AddStudent")
public class ListStudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public ListStudentServlet(StudentService studentService, CourseService courseService,SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = (String) req.getSession().getAttribute("text");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if (text == null || text.isEmpty()) {
            context.setVariable("students", studentService.listAll());
        }
        else {
            context.setVariable("students", studentService.searchByNameOrSurname(text));
        }
        resp.setContentType("application/xhtml+xml");
        this.springTemplateEngine.process("listStudents.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("size");
        Character grade = ' ';
        if (!req.getParameter("grade").isEmpty())
            grade = req.getParameter("grade").charAt(0);
        Long courseId = (Long) req.getSession().getAttribute("courseId");
        try {
            this.courseService.addStudentInCourse(username, courseId, grade);
        } catch (StudentNotFoundException ex) {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("students", studentService.listAll());
            context.setVariable("hasError",true);
            context.setVariable("error", ex.getMessage());
            springTemplateEngine.process("listStudents.html", context, resp.getWriter());
        }
        resp.sendRedirect("/StudentEnrollmentSummary");
    }
}
