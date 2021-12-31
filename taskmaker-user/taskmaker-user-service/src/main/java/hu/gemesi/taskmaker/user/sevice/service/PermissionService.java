package hu.gemesi.taskmaker.user.sevice.service;

import hu.gemesi.taskmaker.common.jpa.service.BaseService;
import hu.gemesi.taskmaker.common.model.user.UserPermission;
import hu.gemesi.taskmaker.common.model.user.UserRole;
import hu.gemesi.taskmaker.user.sevice.repository.PermissionRepository;

import javax.inject.Inject;
import java.util.List;

public class PermissionService extends BaseService<UserPermission> {

    @Inject
    private PermissionRepository permissionRepository;

    public List<UserPermission> findByUserRole(UserRole userRole) {
        return permissionRepository.findByUserRole(userRole);
    }
}
