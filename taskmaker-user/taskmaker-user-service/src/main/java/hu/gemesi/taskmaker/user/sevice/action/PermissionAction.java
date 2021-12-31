package hu.gemesi.taskmaker.user.sevice.action;

import hu.gemesi.taskmaker.rest.service.PermissionService;

import javax.inject.Inject;

/*@Startup
@Singleton*/
public class PermissionAction {

    @Inject
    private PermissionService permissionService;

    /*@PostConstruct
    void atStartup() {
        if (permissionService.findAll(UserPermission.class).size() == 0) {
            for (UserRole userRole : UserRole.values()) {
                var userPermission = new UserPermission();
                userPermission.setUserRole(userRole);
                switch (userRole) {
                case ADMIN:
                    userPermission.setPermission(CREATE_GROUP.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CREATE_TASK.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CLOSABLE_GROUP.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CHECK_RESULT_TASK.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(USER_MANAGEMENT.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(DELETE_USER.value());
                    permissionService.save(userPermission);
                    break;
                case GROUP_ADMIN:
                    userPermission.setPermission(CREATE_GROUP.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CREATE_TASK.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CLOSABLE_GROUP.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(CHECK_RESULT_TASK.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(USER_MANAGEMENT.value());
                    permissionService.save(userPermission);
                    break;
                case TASK_SOLVER:
                    userPermission.setPermission(ADD_SOLUTION_TASK.value());
                    permissionService.save(userPermission);
                    userPermission.setPermission(SOLUTION_REMOVE.value());
                    permissionService.save(userPermission);
                    break;
                }
            }
        }
    }*/
}
