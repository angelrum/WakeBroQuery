package system.model;

import java.util.Date;

/**
 * Created by vladimir on 16.03.2018.
 * Уникальный идентификатор
 */
public abstract class AbstractBaseEntity {

    private Integer id;

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
