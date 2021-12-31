package hu.gemesi.taskmaker.matcher.service.repository;

import hu.gemesi.taskmaker.common.model.group.Group;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends EntityRepository<String, Group> {
    Optional<Group> findByName(String name);
}
