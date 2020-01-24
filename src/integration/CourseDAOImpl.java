package integration;

import entities.Course;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CourseDAOImpl implements DAO<Course> {

    EntityManagerFactory emf = Factory.emf;

    @Override
    public Course findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Course c = findByID(id);
                return c;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            Course c = findByName(input);
            return c;
        }
    }

    @Override
    public Course findByName(String name) {
        EntityManager em = emf.createEntityManager();
        Course c = null;
        try {
            c = em.createQuery("FROM Course c where c.name like :name", Course.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
        }
        em.close();
        return c;
    }

    @Override
    public Course findByID(int id) {
        EntityManager em = emf.createEntityManager();
        Course c = em.find(Course.class, id);
        em.close();
        return c;
    }

    @Override
    public Stream<Course> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stream<Course> listCourses = em.createQuery("from Course c", Course.class).getResultStream();
        em.getTransaction().commit();
        em.close();
        return listCourses;
    }

    @Override
    public void create(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(c));
        em.getTransaction().commit();
        em.close();
    }

}
