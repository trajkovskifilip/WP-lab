package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    private HtmlUnitDriver driver;

    private static Teacher t1;
    private static Teacher t2;
    private static String admin = "admin";
    private static String type1 = "WINTER";
    private static String type2 = "SUMMER";
    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    private void initData() {
        if (!dataInitialized) {
            t1 = teacherService.save("t1", "t1").get();
            t2 = teacherService.save("t2", "t2").get();
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception {
        CoursesPage coursesPage = CoursesPage.to(this.driver);
        coursesPage.assertElements(0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        coursesPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        coursesPage.assertElements(0, 0, 0, 1);
        coursesPage = AddOrEditCourse.addCourse(this.driver, "test", "test description", t1.getId().toString(), type1);
        coursesPage.assertElements(1, 1, 1, 1);
        coursesPage = AddOrEditCourse.addCourse(this.driver, "test1", "test1 description", t2.getId().toString(), type2);
        coursesPage.assertElements(2, 2, 2, 1);
        coursesPage.getDeleteButtons().get(1).click();
        coursesPage.assertElements(1, 1, 1, 1);
        coursesPage = AddOrEditCourse.editCourse(this.driver, coursesPage.getEditButtons().get(0), "test1", "test1 description", t1.getId().toString(), type1);
        coursesPage.assertElements(1, 1, 1, 1);
    }

    @Test
    public void testSearch() throws Exception {
        CoursesPage coursesPage = CoursesPage.to(this.driver);
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        LoginPage.doLogin(this.driver, loginPage, admin, admin);
        AddOrEditCourse.addCourse(this.driver, "Veb programiranje", "test description", t1.getId().toString(), type1);
        coursesPage = AddOrEditCourse.addCourse(this.driver, "Kompjuterski mrezi", "test1 description", t2.getId().toString(), type2);
        coursesPage = CoursesPage.search(this.driver, coursesPage, "Veb");
        coursesPage.assertElements(1, 1, 1, 1);
    }
}
