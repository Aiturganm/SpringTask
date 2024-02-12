package springtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter   @Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_gen")
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", allocationSize = 1)
    private Long id;
    private String name;
    private int price;
    private LocalDate dateOfStart;
    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Lesson> lessons;

    public Course(String name, int price, LocalDate dateOfStart) {
        this.name = name;
        this.price = price;
        this.dateOfStart = dateOfStart;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", dateOfStart=" + dateOfStart +
                '}';
    }
}
