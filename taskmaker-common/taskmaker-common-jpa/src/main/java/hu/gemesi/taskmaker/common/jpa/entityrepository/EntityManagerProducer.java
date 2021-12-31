package hu.gemesi.taskmaker.common.jpa.entityrepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class EntityManagerProducer {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Produces
    @Dependent
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
