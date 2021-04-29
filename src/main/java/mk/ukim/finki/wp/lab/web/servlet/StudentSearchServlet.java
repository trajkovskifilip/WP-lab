package mk.ukim.finki.wp.lab.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentSearchServlet", urlPatterns = "/listSearchedStudent")
public class StudentSearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        req.getSession().setAttribute("text", text);
        resp.sendRedirect("/AddStudent");
    }
}
