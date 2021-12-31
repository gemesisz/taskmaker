package hu.gemesi.taskmaker.matcher.service.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.task.Task;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.matcher.service.repository.GroupRepository;
import hu.gemesi.taskmaker.matcher.service.repository.TaskRepository;
import hu.gemesi.taskmaker.matcher.service.repository.UserRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class TaskService extends BaseService<Task> {

    @Inject
    private GroupRepository groupRepository;

    public Group findByGroupName(String groupName) {
        return groupRepository.findByName(groupName).orElse(null);
    }

    @Inject
    private UserRepository userRepository;

    public CustomerUser findByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Inject
    private TaskRepository taskRepository;

    public Task findByName(String name) {
        return taskRepository.findByName(name).orElse(null);
    }


}
