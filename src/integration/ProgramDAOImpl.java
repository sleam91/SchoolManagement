package integration;

import entities.Program;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProgramDAOImpl implements DAO<Program> {

    EntityManagerFactory emf = Factory.emf;

    @Override
    public Program findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Program p = findByID(id);
                return p;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            Program p = findByName(input);
            return p;
        }
    }

    @Override
    public Program findByName(String name) {
        EntityManager em = emf.createEntityManager();
        Program p = null;
        try {
            p = em.createQuery("FROM Program p where p.name like :name", Program.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
        }
        em.close();
        return p;
    }

    @Override
    public Program findByID(int id) {
        EntityManager em = emf.createEntityManager();
        Program p = em.find(Program.class, id);
        em.close();
        return p;
    }

    @Override
    public Stream<Program> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stream<Program> listProgram = em.createQuery("from Program p", Program.class).getResultStream();
        em.getTransaction().commit();
        em.close();
        return listProgram;
    }

    @Override
    public void create(Program p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Program p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Program p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(p));
        em.getTransaction().commit();
        em.close();
    }

}
