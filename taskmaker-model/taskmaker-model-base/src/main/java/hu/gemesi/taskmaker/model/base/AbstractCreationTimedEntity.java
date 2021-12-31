package hu.gemesi.taskmaker.model.base;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Vetoed
@MappedSuperclass
public class AbstractCreationTimedEntity extends AbstractEntity {

    @Column(name = "creation_date")
    protected LocalDateTime creationDate;

    @PrePersist
    private void init() {
        setCreationDate(LocalDateTime.now());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
