package springtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springtask.entity.Course;
import springtask.repository.GenericRepository;
import springtask.repository.impl.CourseRepository;
import springtask.service.GenericService;

import java.util.List;

@Service
public class CourseService implements GenericService<Course, Long> {
    private final GenericRepository genericRepository;

    @Autowired
    public CourseService(@Qualifier("courseRepository") GenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public Course save(Course entity) {
        return (Course) genericRepository.save(entity);
    }

    @Override
    public Course findById(Long aLong) {
        if(!getAll().isEmpty()){
            return (Course) genericRepository.findById(aLong);
        }else {
            return null;
        }
    }

    @Override
    public List<Course> getAll() {
        return genericRepository.getAll();
    }

    @Override
    public Course updateById(Long aLong, Course newEntity) {
        if (!getAll().isEmpty()) {
            return (Course) genericRepository.updateById(aLong, newEntity);
        }else{
            System.err.println("Course with id " + aLong + " not found!");
            return null;
        }
    }

    @Override
    public void deleteById(Long aLong) {
        if(findById(aLong) != null){
            genericRepository.deleteById(aLong);
            System.out.println("Course with id " + aLong + " successfully deleted!");
        }else System.err.println("Course with id " + aLong + " not found!");
    }
}
