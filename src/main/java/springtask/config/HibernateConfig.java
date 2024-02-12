
package springtask.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springtask.entity.Course;
import springtask.entity.Lesson;
import springtask.entity.Student;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "springtask")
public class HibernateConfig {
    @Bean
    public static SessionFactory getSession(){
        Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5433/postgres");
        properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "postgres");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Lesson.class);
        return configuration.buildSessionFactory();
    }

    @Bean
    public static EntityManagerFactory getEntityManager(){
        try{
            return getSession().unwrap(EntityManagerFactory.class);
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
