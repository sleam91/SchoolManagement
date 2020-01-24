package integration;

import entities.Teacher;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TeacherDAOImpl implements DAO<Teacher> {

    EntityManagerFactory emf = Factory.emf;

    @Override
    public Teacher findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Teacher t = findByID(id);
                return t;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            Teacher t = findByName(input);
            return t;
        }
    }

    @Override
    public Teacher findByName(String name) {
        EntityManager em = emf.createEntityManager();

        Teacher t = null;
        try {
            t = em.createQuery("FROM Teacher t where t.name like :name", Teacher.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
        }
        em.close();
        return t;
    }

    @Override
    public Teacher findByID(int id) {
        EntityManager em = emf.createEntityManager();
        Teacher t = em.find(Teacher.class, id);
        em.close();
        return t;
    }

    @Override
    public Stream<Teacher> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stream<Teacher> listTeacher = em.createQuery("from Teacher t", Teacher.class).getResultStream();
        em.getTransaction().commit();
        em.close();
        return listTeacher;
    }

    @Override
    public void create(Teacher t) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Teacher t) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Teacher t) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(t));
        em.getTransaction().commit();
        em.close();
    }

}
