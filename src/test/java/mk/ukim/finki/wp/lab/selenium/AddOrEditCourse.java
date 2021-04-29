package mk.ukim.finki.wp.lab.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditCourse extends AbstractPage {
    private WebElement name;
    private WebElement description;
    private WebElement teacherId;
    private WebElement type;
    private WebElement submit;

    public AddOrEditCourse(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage addCourse(WebDriver driver, String name, String description, String teacherId, String type) {
        get(driver, "/courses/add-form");
        AddOrEditCourse addOrEditCourse = PageFactory.initElements(driver, AddOrEditCourse.class);
        addOrEditCourse.name.sendKeys(name);
        addOrEditCourse.description.sendKeys(description);
        addOrEditCourse.teacherId.click();
        addOrEditCourse.teacherId.findElement(By.xpath("//div[3]/select/option[" + teacherId + "]")).click();
        addOrEditCourse.type.click();
        addOrEditCourse.type.findElement(By.xpath("//div[4]/select/option[. = '" + type + "']")).click();
        addOrEditCourse.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public static CoursesPage editCourse(WebDriver driver, WebElement editButton, String name, String description, String teacherId, String type) {
        editButton.click();
        System.out.println(driver.getCurrentUrl());
        AddOrEditCourse addOrEditCourse = PageFactory.initElements(driver, AddOrEditCourse.class);
        addOrEditCourse.name.sendKeys(name);
        addOrEditCourse.description.sendKeys(description);
        addOrEditCourse.teacherId.click();
        addOrEditCourse.teacherId.findElement(By.xpath("//div[3]/select/option[" + teacherId + "]")).click();
        addOrEditCourse.type.click();
        addOrEditCourse.type.findElement(By.xpath("//div[4]/select/option[. = '" + type + "']")).click();
        addOrEditCourse.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);

    }
}
