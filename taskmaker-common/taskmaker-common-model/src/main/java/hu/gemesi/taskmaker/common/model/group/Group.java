package hu.gemesi.taskmaker.common.model.group;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Group_TaskMaker")
@Vetoed
public class Group extends AbstractCreationTimedEntity {

    @Column(name="name")
    private String name;

    @OneToOne
    @JoinColumn(name = "admin")
    private CustomerUser admin;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy ="group", fetch = FetchType.EAGER)
    private List<Grouping> groupingList;

    @PrePersist
    private void preInit() {
        setActive(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerUser getAdmin() {
        return admin;
    }

    public void setAdmin(CustomerUser admin) {
        this.admin = admin;
    }

    public List<Grouping> getGroupingList() {
        return groupingList;
    }

    public void setGroupingList(List<Grouping> groupingList) {
        this.groupingList = groupingList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
