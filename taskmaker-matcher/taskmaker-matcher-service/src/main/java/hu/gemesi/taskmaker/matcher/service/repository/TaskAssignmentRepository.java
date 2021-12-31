package hu.gemesi.taskmaker.matcher.service.repository;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.task.TaskAssignment;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface TaskAssignmentRepository extends EntityRepository<String, TaskAssignment> {

    List<TaskAssignment> findByGroup(Group group);

    List<TaskAssignment> findByGroupAndUser(Group group, CustomerUser customerUser);
}
