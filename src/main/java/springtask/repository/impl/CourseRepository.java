package springtask.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springtask.config.HibernateConfig;
import springtask.entity.Course;
import springtask.repository.GenericRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository implements GenericRepository<Course, Long> {
    @PersistenceContext
    private EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManager();

//    @Autowired
//    public CourseRepository(EntityManagerFactory entityManagerFactory) {
//        this.entityManagerFactory = entityManagerFactory;
//    }

    @Override
    public Course save(Course entity) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public Course findById(Long aLong) {
        Course course = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
             course = entityManager.find(Course.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courseList = new ArrayList<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            courseList = entityManager.createQuery("select c from Course c", Course.class).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        entityManager.close();
        return courseList;
    }

    @Override
    public Course updateById(Long aLong, Course newEntity) {
        String query = "update Course c set c.name = :parName, c.price = :parPrice, c.dateOfStart = :parDate where id = :parID";
        Course course = null;
        try( EntityManager entityManager = entityManagerFactory.createEntityManager();){
            entityManager.getTransaction().begin();
            entityManager.createQuery(query)
                    .setParameter("parName", newEntity.getName())
                    .setParameter("parPrice", newEntity.getPrice())
                    .setParameter("parDate", newEntity.getDateOfStart())
                    .setParameter("parID", aLong).executeUpdate();
            course = entityManager.find(Course.class, aLong);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return course;
    }

    @Override
    public void deleteById(Long aLong) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Course course = null;
        try {
            entityManager.getTransaction().begin();
            course = entityManager.find(Course.class, aLong);
            entityManager.remove(course);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally {
            entityManager.close();
        }
    }
}
