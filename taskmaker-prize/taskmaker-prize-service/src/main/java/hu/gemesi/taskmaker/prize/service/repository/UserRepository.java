package hu.gemesi.taskmaker.prize.service.repository;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;

import java.util.Optional;

public interface UserRepository extends EntityRepository<CustomerUser, String> {

    Optional<CustomerUser> findByUsername(String username);
}
