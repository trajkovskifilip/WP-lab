package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateStudentServlet", urlPatterns = "/createStudent")
public class CreateStudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public CreateStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setContentType("application/xhtml+xml");
        this.springTemplateEngine.process("createStudent.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        try {
            studentService.save(username, password, name, surname);
        } catch (InvalidArgumentsException ex) {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("hasError",true);
            context.setVariable("error", ex.getMessage());
            springTemplateEngine.process("createStudent.html", context, resp.getWriter());
        }
        resp.sendRedirect("/AddStudent");
    }
}
