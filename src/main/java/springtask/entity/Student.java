package springtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "students")
@Getter   @Setter@NoArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_gen")
    @SequenceGenerator(name="student_gen", sequenceName = "student_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private int yearOfBirth;
    @ManyToOne
    private Course course;

    public Student(String name, String email, int yearOfBirth, Course course) {
        this.name = name;
        this.email = email;
        this.yearOfBirth = yearOfBirth;
        this.course = course;
    }
}
