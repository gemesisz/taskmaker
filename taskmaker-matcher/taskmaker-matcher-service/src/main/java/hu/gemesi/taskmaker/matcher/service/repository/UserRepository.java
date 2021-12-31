package hu.gemesi.taskmaker.matcher.service.repository;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends EntityRepository<String, CustomerUser> {
    Optional<CustomerUser> findByUsername(String username);
}
