package hu.gemesi.taskmaker.user.sevice.repository;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends EntityRepository<String, Group> {

    List<Group> findByAdmin(CustomerUser admin);

    Optional<Group> findByName(String name);
}
