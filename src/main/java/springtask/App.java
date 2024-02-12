package springtask;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springtask.config.HibernateConfig;
import springtask.entity.Course;
import springtask.entity.Lesson;
import springtask.entity.Student;
import springtask.repository.impl.CourseRepository;
import springtask.service.GenericService;
import springtask.service.impl.CourseService;
import springtask.service.impl.LessonService;
import springtask.service.impl.StudentService;

import java.time.LocalDate;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GenericService<Course, Long> courseService = new CourseService(new CourseRepository());
//        System.out.println(courseService.save(new Course("Java", 14000, LocalDate.of(2023, 10, 1))));
//        System.out.println(courseService.save(new Course("Flutter", 15000, LocalDate.of(2024, 02, 9))));
//        System.out.println(courseService.save(new Course("JS", 14000, LocalDate.of(2024, 1, 1))));

//        System.out.println(courseService.findById(2L));

//        List<Course> courses = courseService.getAll();
//        for (Course course : courses) {
//            System.out.println(course);
//        }

//        System.out.println(courseService.updateById(2L, new Course("C#", 12000, LocalDate.of(2024, 3, 1))));

//        courseService.deleteById(4L);

        GenericService<Lesson, Long> lessonService = new LessonService();
//        lessonService.save(new Lesson("Varargs", "JAVA Lesson", "https://varargs2u457.com", LocalDate.of(2024, 2, 10), true));
//        lessonService.save(new Lesson("CSS", "JS Lesson", "https://jscsspeaksoft.com", LocalDate.of(2024, 1, 28), false));
//        lessonService.save(new Lesson("Arrays", "JAVA Lesson", "https://arrays754rh.com", LocalDate.of(2024, 1, 19), true));

//        System.out.println(lessonService.findById(1L));

//        List<Lesson> all = lessonService.getAll();
//        for (Lesson lesson : all) {
//            System.out.println(lesson);
//        }

//        System.out.println(lessonService.updateById(3L, new Lesson("Spring", "JAVA Lesson", "https://springframework23.com", LocalDate.of(2024, 2, 8), true)));

//        lessonService.deleteById(2L);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);
        GenericService<Student, Long> studentService = context.getBean("studentService", StudentService.class);
//        studentService.save(new Student("Kasymbekov Dastan", "dastan@gmail.com", 2004, courseService.findById(2L)));
//        studentService.save(new Student("Kanybek uulu Sanjar", "kanybekuulu04@gmail.com", 2005, courseService.findById(1L)));

//        System.out.println(studentService.findById(2L));

//        List<Student> all = studentService.getAll();
//        for (Student student : all) {
//            System.out.println(student);
//        }

//        System.out.println(studentService.updateById(2L, new Student("Ulanbek uulu Erjan", "erjan05@gmail.com", 2005, courseService.findById(3L))));

//        studentService.deleteById(1L);

    }
}
