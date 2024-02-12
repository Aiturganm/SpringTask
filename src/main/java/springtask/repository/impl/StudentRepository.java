package springtask.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import springtask.entity.Student;
import springtask.repository.GenericRepository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class StudentRepository implements GenericRepository<Student, Long> {
    EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(@Qualifier("getEntityManager") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Student save(Student entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return entity;
    }

    @Override
    public Student findById(Long aLong) {
        Student student = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            student = entityManager.find(Student.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> studentList = new ArrayList<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            studentList = entityManager.createQuery("select s from Student s").getResultList();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return studentList;
    }

    @Override
    public Student updateById(Long aLong, Student newEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = null;
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("""
                    update Student s 
                    set s.name = :parName, 
                    s.email = :parEmail,
                    s.yearOfBirth = :parYear,
                    s.course = :parCourse
                    where id = :parId
                    """)
                    .setParameter("parName", newEntity.getName())
                    .setParameter("parEmail", newEntity.getEmail())
                    .setParameter("parYear", newEntity.getYearOfBirth())
                    .setParameter("parCourse", newEntity.getCourse())
                    .setParameter("parId", aLong).executeUpdate();
            student = entityManager.find(Student.class, aLong);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return student;
    }

    @Override
    public void deleteById(Long aLong) {
        String query = "delete from Student s where id = :parId";
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.createQuery(query).setParameter("parId", aLong).executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
