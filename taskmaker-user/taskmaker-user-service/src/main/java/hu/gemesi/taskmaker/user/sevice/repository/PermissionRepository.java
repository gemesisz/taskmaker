package hu.gemesi.taskmaker.user.sevice.repository;

import hu.gemesi.taskmaker.common.model.user.UserPermission;
import hu.gemesi.taskmaker.common.model.user.UserRole;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends EntityRepository<UserPermission, String> {

    List<UserPermission> findByUserRole(UserRole userRole);
}
