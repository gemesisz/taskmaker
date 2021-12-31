package hu.gemesi.taskmaker.common.model.task;

import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
@Vetoed
public class Task extends AbstractCreationTimedEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "point")
    private int point;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomerUser creator;

    @OneToMany(mappedBy = "task")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TaskAssignment> taskAssignmentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public CustomerUser getCreator() {
        return creator;
    }

    public void setCreator(CustomerUser creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        return getId().equals(((Task) obj).getId());
    }

    public List<TaskAssignment> getTaskAssignmentList() {
        return taskAssignmentList;
    }

    public void setTaskAssignmentList(List<TaskAssignment> taskAssignmentList) {
        this.taskAssignmentList = taskAssignmentList;
    }
}
