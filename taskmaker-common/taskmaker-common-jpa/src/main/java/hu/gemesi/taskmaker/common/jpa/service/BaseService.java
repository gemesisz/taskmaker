package hu.gemesi.taskmaker.common.jpa.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Dependent
public class BaseService<T> {

    @Inject
    private EntityManager entityManager;

    public T findById(String id, Class<T> clazz) {
        return getEntityManager().find(clazz, id);
    }

    public List<T> findAll(Class<T> clazz) {
        return getEntityManager().createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
    }

    public T save(T entity) {
        T savedEntity = getEntityManager().merge(entity);
        getEntityManager().flush();
        getEntityManager().refresh(savedEntity);
        return savedEntity;
    }

    public void delete(T entity) {
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public static String likeParameter(String string) {
        StringBuffer sb = new StringBuffer();
        sb.append("%").append(string).append("%");
        return sb.toString();
    }

    public static String afterLikeParameter(String string) {
        StringBuffer sb = new StringBuffer();
        sb.append(string).append("%");
        return sb.toString();
    }

    public static String beforeLikeParameter(String string) {
        StringBuffer sb = new StringBuffer();
        sb.append("%").append(string);
        return sb.toString();
    }
}
