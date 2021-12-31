package hu.gemesi.taskmaker.user.sevice.repository;

import hu.gemesi.taskmaker.common.model.user.SecurityUser;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends EntityRepository<SecurityUser, String> {

    Optional<SecurityUser> findByUsernameAndPassword(String username, String password);

    Optional<SecurityUser> findByUsername(String username);
}
