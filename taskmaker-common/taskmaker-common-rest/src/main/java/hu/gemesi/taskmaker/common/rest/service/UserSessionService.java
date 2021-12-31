package hu.gemesi.taskmaker.common.rest.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.user.UserSession;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserSessionService extends BaseService<UserSession> {

    public UserSession findByUserName(String username) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserSession> criteriaQuery = criteriaBuilder.createQuery(UserSession.class);
        Root<UserSession> emp = criteriaQuery.from(UserSession.class);
        criteriaQuery.select(emp);
        criteriaQuery.where(criteriaBuilder.equal(emp.get("username"), username));
        List<UserSession> userSessionList = getEntityManager().createQuery(criteriaQuery).getResultList();
        if (userSessionList.size() != 1) {
            return null;
        }
        return userSessionList.get(0);
    }

    public List<UserSession> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserSession> criteriaQuery = criteriaBuilder.createQuery(UserSession.class);
        Root<UserSession> emp = criteriaQuery.from(UserSession.class);
        criteriaQuery.select(emp);
        criteriaQuery.where(criteriaBuilder.equal(emp.get("username"), username));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
}
