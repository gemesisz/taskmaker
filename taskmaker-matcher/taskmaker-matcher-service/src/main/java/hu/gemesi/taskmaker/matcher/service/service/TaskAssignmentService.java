package hu.gemesi.taskmaker.matcher.service.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.task.TaskAssignment;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.matcher.service.repository.TaskAssignmentRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class TaskAssignmentService extends BaseService<TaskAssignment> {

    @Inject
    private TaskAssignmentRepository taskAssignmentRepository;

    public List<TaskAssignment> findByGroup(Group group) {
        return taskAssignmentRepository.findByGroup(group);
    }

    public List<TaskAssignment> findByGroupAndUser(Group group, CustomerUser customerUser) {
        return taskAssignmentRepository.findByGroupAndUser(group, customerUser);
    }
}
