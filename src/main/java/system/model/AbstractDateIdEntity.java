package system.model;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 */
public abstract class AbstractDateIdEntity extends AbstractDateEntity {

    private Integer createId;

    public AbstractDateIdEntity(Integer id, LocalDateTime create, Integer createId) {
        super(id, create);
        this.createId = createId;
    }

    public AbstractDateIdEntity(Integer id, Integer createId) {
        super(id);
        this.createId = createId;
    }

    public AbstractDateIdEntity(Integer createId) {
        this(null, createId);
    }

    public Integer getCreateId() {
        return createId;
    }
}
