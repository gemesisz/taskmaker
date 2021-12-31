package hu.gemesi.taskmaker.common.model.task;


import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.model.base.AbstractCreationTimedEntity;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Task_Assignment")
@Vetoed
public class TaskAssignment extends AbstractCreationTimedEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomerUser customerUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "point")
    private int point;

    @Column(name = "optionalSolution")
    private String optionalSolution;

    @Column(name = "resultComment")
    private String resultComment;

    @Column(name = "solutionFile")
    private String fileName;

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getOptionalSolution() {
        return optionalSolution;
    }

    public void setOptionalSolution(String optionalSolution) {
        this.optionalSolution = optionalSolution;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }
}
