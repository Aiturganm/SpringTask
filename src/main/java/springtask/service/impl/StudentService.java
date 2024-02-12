package springtask.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springtask.entity.Student;
import springtask.repository.GenericRepository;
import springtask.repository.impl.StudentRepository;
import springtask.service.GenericService;

import java.util.List;

@Service
public class StudentService implements GenericService<Student, Long> {
    private GenericRepository genericRepository;
    @Autowired
    public void setStudentRepository(@Qualifier("studentRepository") GenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public Student save(Student entity) {
        return (Student) genericRepository.save(entity);
    }

    @Override
    public Student findById(Long aLong) {
        if (!getAll().isEmpty()) {
            return (Student) genericRepository.findById(aLong);
        }
        else{
            System.err.println("With id " + aLong + " not found!");
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        return genericRepository.getAll();
    }

    @Override
    public Student updateById(Long aLong, Student newEntity) {
        if(findById(aLong) != null){
            System.out.println("Successfully updated student!");
            return (Student) genericRepository.updateById(aLong, newEntity);
        }else System.err.println("Student with id " + aLong + " not found!");
        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        if(findById(aLong) == null){
            System.err.println("Student with id " + aLong + " not found!");
        }else if (findById(aLong) != null) {
            genericRepository.deleteById(aLong);
            System.out.println("Student successfully deleted!");
        }
    }
}
