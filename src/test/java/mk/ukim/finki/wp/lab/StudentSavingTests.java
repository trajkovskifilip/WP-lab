package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentSavingTests {

    @Mock
    private StudentRepository studentRepository;

    private StudentService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Student student = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        this.service = Mockito.spy(new StudentServiceImpl(this.studentRepository));
    }

    @Test
    public void testSuccessfulSaving() {
        Student student = this.service.save("username", "password", "name", "surname");
        Mockito.verify(this.service).save("username", "password", "name", "surname");

        Assert.assertNotNull("Student is null", student);
        Assert.assertEquals("Username do not match", "username", student.getUsername());
        Assert.assertEquals("Passwords do not match", "password", student.getPassword());
        Assert.assertEquals("Names do not match", "name", student.getName());
        Assert.assertEquals("Surnames do not match", "surname", student.getSurname());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.save(null, "password", "name", "surname"));
        Mockito.verify(this.service).save(null, "password", "name", "surname");
    }

    @Test
    public void testEmptyUsername() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.save("", "password", "name", "surname"));
        Mockito.verify(this.service).save("", "password", "name", "surname");
    }

    @Test
    public void testNullPassword() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.save("username", null, "name", "surname"));
        Mockito.verify(this.service).save("username", null, "name", "surname");
    }

    @Test
    public void testEmptyPassword() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.save("username", "", "name", "surname"));
        Mockito.verify(this.service).save("username", "", "name", "surname");
    }
}
