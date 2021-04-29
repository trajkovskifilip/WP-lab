package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CoursesPage extends AbstractPage {
    @FindBy(css = "tr[class=course]")
    private List<WebElement> coursesRows;

    @FindBy(css = ".delete-course")
    private List<WebElement> deleteButtons;

    @FindBy(css = ".edit-course")
    private List<WebElement> editButtons;

    @FindBy(className = "add-course")
    private List<WebElement> addButtons;

    private WebElement text;

    private WebElement search;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage to(WebDriver driver) {
        get(driver, "/courses");
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public static CoursesPage search(WebDriver driver, CoursesPage coursesPage, String text) {
        coursesPage.text.sendKeys(text);
        coursesPage.search.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public void assertElements(int coursesNumber, int deleteButtons, int editButtons, int addButtons) {
        Assert.assertEquals("Rows do not match", coursesNumber, this.getCoursesRows().size());
        Assert.assertEquals("Delete buttons do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("Edit buttons do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("Add button is visible", addButtons, this.getAddButtons().size());
    }
}
