package hu.gemesi.taskmaker.common.model.user;

import hu.gemesi.taskmaker.model.base.AbstractEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "Permission")
@Vetoed
public class UserPermission extends AbstractEntity {

    @Column(name = "userRole")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "permission")
    private String permission;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
