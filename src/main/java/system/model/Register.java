package system.model;

import java.util.Date;

/**
 * Created by vladimir on 17.03.2018.
 * Регистрация открытия и закрытия смены пользователем
 */
public class Register extends AbstractBaseEntity {

    private Date openshift;

    private Date closeshift;

    public Register(Integer id, Date openshift, Date closeshift) {
        super(id);
        this.openshift = openshift;
        this.closeshift = closeshift;
    }

    public Register(Integer id, Date openshift) {
        super(id);
        this.openshift = openshift;
    }

    public void setCloseshift(Date closeshift) {
        this.closeshift = closeshift;
    }
}
