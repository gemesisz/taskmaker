package hu.gemesi.taskmaker.common.model.group;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;

@Entity
@Table(name = "Grouping")
@Vetoed
public class Grouping extends AbstractCreationTimedEntity {

    @ManyToOne
    @JoinColumn(name = "group_id")
    protected Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected CustomerUser customerUser;

    public Grouping() {
    }

    public Grouping(CustomerUser customerUser, Group group) {

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public CustomerUser getUser() {
        return customerUser;
    }

    public void setUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }
}
