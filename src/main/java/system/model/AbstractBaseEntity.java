package system.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;

/**
 * Created by vladimir on 16.03.2018.
 * Уникальный идентификатор
 * @Access управляет доступом (поля или св-ва)
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    private static final int START_SEQ = 100_000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id==null;
    }


}
