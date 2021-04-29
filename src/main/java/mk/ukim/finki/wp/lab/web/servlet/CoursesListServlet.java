package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CoursesListServlet", urlPatterns = "/listCourses")
public class CoursesListServlet extends HttpServlet {

    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public CoursesListServlet(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("courses", courseService.listAll());
        resp.setContentType("application/xhtml+xml");
        this.springTemplateEngine.process("listCourses.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("courseId");
        if (id == null) {
            resp.sendRedirect("/courses");
        }
        else {
            Long courseId = Long.parseLong(id);
            req.getSession().setAttribute("courseId", courseId);
            resp.sendRedirect("/AddStudent");
        }
    }
}
