package hu.gemesi.taskmaker.common.model.user;

import hu.gemesi.taskmaker.model.base.AbstractEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "security_user")
@Vetoed
public class SecurityUser extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "user")
    private CustomerUser customerUser;

    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
