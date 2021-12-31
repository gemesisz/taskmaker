package hu.gemesi.taskmaker.common.model.user;

import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer_user")
@Vetoed
public class CustomerUser extends AbstractCreationTimedEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "email")
    private String email;

    //@OneToMany(mappedBy = "customerUser", fetch = FetchType.EAGER)
    //protected List<Grouping> groupingList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Grouping> getGroupingList() {
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        return getId().equals(((CustomerUser) obj).getId());
    }
}
