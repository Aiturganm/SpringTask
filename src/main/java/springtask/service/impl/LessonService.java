package springtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import springtask.entity.Lesson;
import springtask.repository.GenericRepository;
import springtask.repository.impl.LessonRepository;
import springtask.service.GenericService;

import java.util.List;

@Service
@Scope("singleton")
public class LessonService implements GenericService<Lesson, Long> {
    private final GenericRepository<Lesson, Long> genericService;

    @Autowired
    public LessonService(@Qualifier("lessonRepository") GenericRepository<Lesson, Long> genericService) {
        this.genericService = genericService;
    }
    @Override
    public Lesson save(Lesson entity) {
        return genericService.save(entity);
    }

    @Override
    public Lesson findById(Long aLong) {
        if (!getAll().isEmpty()) {
            return genericService.findById(aLong);
        }
        else{
            System.err.println("With id " + aLong + " not found!");
        }
        return null;
    }

    @Override
    public List<Lesson> getAll() {
        return genericService.getAll();
    }

    @Override
    public Lesson updateById(Long aLong, Lesson newEntity) {
        if(findById(aLong) != null){
            System.out.println("Successfully updated lesson!");
            return genericService.updateById(aLong, newEntity);
        }else System.err.println("Lesson with id " + aLong + " not found!");
        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        if(findById(aLong) == null){
            System.err.println("Lesson with id " + aLong + " not found!");
        }else if (findById(aLong) != null) {
            genericService.deleteById(aLong);
            System.out.println("Lesson successfully deleted!");
        }
    }
}
