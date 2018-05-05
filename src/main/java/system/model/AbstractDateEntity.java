package system.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Уникальный идентификатор и дата создания записи
 */
@MappedSuperclass
public abstract class AbstractDateEntity extends AbstractBaseEntity {

    @Column(name = "date_create")
    private LocalDateTime create;

    public AbstractDateEntity(Integer id, @NotNull LocalDateTime create) {
        super(id);
        this.create = create;
    }

    public AbstractDateEntity(Integer id) {
        super(id);
        this.create = LocalDateTime.now();
    }

    public AbstractDateEntity(@NotNull LocalDateTime create) {
        this.create = create;
    }

    public AbstractDateEntity() {
        this.create = LocalDateTime.now();
    }

    public LocalDateTime getCreate() {
        return create;
    }
}
