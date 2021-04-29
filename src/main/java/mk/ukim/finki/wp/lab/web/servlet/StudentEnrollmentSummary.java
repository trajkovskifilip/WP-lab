package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {

    private final CourseService courseService;
    private final GradeService gradeService;
    private final SpringTemplateEngine springTemplateEngine;

    public StudentEnrollmentSummary(CourseService courseService,
                                    GradeService gradeService,
                                    SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Long courseId = (Long) req.getSession().getAttribute("courseId");
        context.setVariable("course", courseService.searchById(courseId).get());
        context.setVariable("grades", gradeService.listStudentsGrades(courseId));
        resp.setContentType("application/xhtml+xml");
        this.springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }
}
