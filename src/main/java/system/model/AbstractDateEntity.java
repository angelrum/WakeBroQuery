package system.model;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Уникальный идентификатор и дата создания записи
 */
public abstract class AbstractDateEntity extends AbstractBaseEntity {

    private LocalDateTime create;

    public AbstractDateEntity(Integer id, LocalDateTime create) {
        super(id);
        this.create = create;
    }

    public AbstractDateEntity(Integer id) {
        super(id);
        this.create = LocalDateTime.now();
    }

    public LocalDateTime getCreate() {
        return create;
    }
}
