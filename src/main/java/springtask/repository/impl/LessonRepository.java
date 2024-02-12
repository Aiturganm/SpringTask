package springtask.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import springtask.config.HibernateConfig;
import springtask.entity.Course;
import springtask.entity.Lesson;
import springtask.repository.GenericRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
public class LessonRepository implements GenericRepository<Lesson, Long> {
    @Autowired
    @Qualifier("getEntityManager")
    EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManager();

    @Override
    public Lesson save(Lesson entity) {
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
    public Lesson findById(Long aLong) {
        Lesson lesson = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lesson = entityManager.find(Lesson.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return lesson;
    }

    @Override
    public List<Lesson> getAll() {
        List<Lesson> lessonList = new ArrayList<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            lessonList = entityManager.createQuery("select l from Lesson l").getResultList();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return lessonList;
    }

    @Override
    public Lesson updateById(Long aLong, Lesson newEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Lesson lesson = null;
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("""
                    update Lesson l 
                    set l.description = :parDesc, 
                    l.title = :parTitle,
                    l.videoLink = :parLink,
                    l.publishedDate = :parDate, 
                    l.isPresentation = :parBoolean
                    where id = :parId
                    """)
                    .setParameter("parDesc", newEntity.getDescription())
                    .setParameter("parTitle", newEntity.getTitle())
                    .setParameter("parLink", newEntity.getVideoLink())
                    .setParameter("parDate", newEntity.getPublishedDate())
                    .setParameter("parBoolean", newEntity.isPresentation())
                    .setParameter("parId", aLong).executeUpdate();
            lesson = entityManager.find(Lesson.class, aLong);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if(entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return lesson;
    }

    @Override
    public void deleteById(Long aLong) {
        String query = "delete from Lesson l where id = :parId";
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.createQuery(query).setParameter("parId", aLong).executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
