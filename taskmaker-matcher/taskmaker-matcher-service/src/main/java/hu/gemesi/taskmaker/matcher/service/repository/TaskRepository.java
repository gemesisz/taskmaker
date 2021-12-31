package hu.gemesi.taskmaker.matcher.service.repository;

import hu.gemesi.taskmaker.common.model.task.Task;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends EntityRepository<String, Task> {

    Optional<Task> findByName(String name);
}
