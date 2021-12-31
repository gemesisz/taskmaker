package hu.gemesi.taskmaker.model.base;

import org.hibernate.annotations.GenericGenerator;

import javax.enterprise.inject.Vetoed;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Vetoed
@MappedSuperclass
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "X__ID")
    @GenericGenerator(name="id-generator", strategy = "hu.gemesi.taskmaker.model.base.generator.EntityIdGenerator")
    @GeneratedValue(generator="id-generator", strategy = GenerationType.IDENTITY)
    protected String id;

    @Column(name = "X__VERSION")
    protected int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
