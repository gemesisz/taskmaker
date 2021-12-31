package hu.gemesi.taskmaker.common.model.prize;

import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Prize extends AbstractCreationTimedEntity {

    @Column(name = "prizeType")
    @Enumerated(EnumType.STRING)
    private String prizeType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomerUser customerUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public CustomerUser getUser() {
        return customerUser;
    }

    public void setUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
