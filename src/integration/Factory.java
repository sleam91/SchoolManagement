package integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SchoolManagementPU");
}
