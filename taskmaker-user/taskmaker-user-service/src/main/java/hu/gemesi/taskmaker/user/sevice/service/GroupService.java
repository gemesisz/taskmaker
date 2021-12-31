package hu.gemesi.taskmaker.user.sevice.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.user.sevice.repository.GroupRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class GroupService extends BaseService<Group> {

    @Inject
    private GroupRepository groupRepository;

    public List<Group> findByAdmin(CustomerUser admin) {
        return groupRepository.findByAdmin(admin);
    }

    public Group findByName(String groupName) {
        return groupRepository.findByName(groupName).orElse(null);
    }
}
