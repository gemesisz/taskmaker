package hu.gemesi.taskmaker.user.sevice.helper;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.rest.client.MatcherClient;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.user.user.PermissionEnumType;
import hu.gemesi.taskmaker.dto.user.user.PermissionMap;
import hu.gemesi.taskmaker.dto.user.user.PermissionType;
import hu.gemesi.taskmaker.user.sevice.service.GroupService;
import hu.gemesi.taskmaker.user.sevice.service.PermissionService;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@Dependent
public class UserPermissionHelper {

    @Inject
    private MatcherClient matcherClient;

    @Inject
    private GroupService groupService;

    @Inject
    private UserLoginHelper userLoginHelper;

    @Inject
    private PermissionService permissionService;

    public boolean checkPermission(PermissionType permissionType, CustomerUser customerUser) {
        if (!userLoginHelper.isLoggedIn(customerUser.getUsername())) {
            return false;
        }
        if (!basicPermissions(customerUser, permissionType.getPermission())) {
            return false;
        }
        PermissionMap permissionMap = permissionType.getParameterMap();
        switch (permissionType.getPermission()) {
            case CHECK_RESULT_TASK:
                return checkTaskResultPermission(permissionMap, customerUser);
            case CREATE_TASK:
            case USER_MANAGEMENT:
            case CLOSABLE_GROUP:
                return groupPermission(permissionMap, customerUser);
            case ADD_SOLUTION_TASK:
                return solutionPermission(permissionMap, customerUser);
            case ACTIVE_GROUP:
                return activeGroup(permissionMap);
            case SOLUTION_REMOVE:
                return checkTaskSolutionRemovePermission(permissionMap, customerUser);
            case DELETE_USER:
            case CREATE_GROUP:
                return true;
        }
        return false;
    }

    private boolean basicPermissions(CustomerUser customerUser, PermissionEnumType permissionEnumType) {
        /*List<UserPermission> userPermissionList = permissionService.findByUserRole(customerUser.getRole());
        for (UserPermission userPermission : userPermissionList) {
            if (userPermission.getPermission().equals(permissionEnumType.value())) {
                return true;
            }
        }*/
        return false;
    }

    private boolean activeGroup(PermissionMap permissionMap) {
        String groupName = permissionMap.get("GROUP_NAME");
        Group group = groupService.findByName(groupName);
        return group.isActive();
    }

    private boolean groupPermission(PermissionMap permissionMap, CustomerUser customerUser) {
        String groupName = permissionMap.get("GROUP_NAME");
        Group group = groupService.findByName(groupName);
        return group.getAdmin().equals(customerUser) && group.isActive();
    }

    private boolean solutionPermission(PermissionMap permissionMap, CustomerUser customerUser) {
        String groupName = permissionMap.get("GROUP_NAME");
        Group group = groupService.findByName(groupName);
        String taskName = permissionMap.get("TASK_NAME");
        try {
            HttpResponse httpResponse = matcherClient.getCheckTaskSolutionAdding(taskName);
            String stringEntity = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            return !group.getAdmin().equals(customerUser) && group.isActive() && stringEntity.contains("200");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean checkTaskSolutionRemovePermission(PermissionMap permissionMap, CustomerUser customerUser) {
        String groupName = permissionMap.get("GROUP_NAME");
        Group group = groupService.findByName(groupName);
        String taskName = permissionMap.get("TASK_NAME");
        try {
            HttpResponse httpResponse = matcherClient.getCheckTaskSolutionRemove(taskName, customerUser.getUsername());
            String stringEntity = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            return !group.getAdmin().equals(customerUser) && group.isActive() && stringEntity.contains("200");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkTaskResultPermission(PermissionMap permissionMap, CustomerUser customerUser) {
        String groupName = permissionMap.get("GROUP_NAME");
        Group group = groupService.findByName(groupName);
        String taskName = permissionMap.get("TASK_NAME");
        try {
            HttpResponse httpResponse = matcherClient.getCheckTaskResult(taskName);
            String stringEntity = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            return group.getAdmin().equals(customerUser) && group.isActive() && stringEntity.contains("200");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
