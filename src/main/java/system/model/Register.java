package system.model;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Регистрация открытия и закрытия смены пользователем
 * @id идентификатор пользователя (user)
 */
public class Register extends AbstractBaseEntity {

    private int userId;

    private LocalDateTime openshift;

    private LocalDateTime closeshift;

    public Register(Integer userid, LocalDateTime openshift, LocalDateTime closeshift) {
        this.userId = userid;
        this.openshift = openshift;
        this.closeshift = closeshift;
    }

    public Register(Integer userid, LocalDateTime openshift) {
        super(userid);
        this.openshift = openshift;
    }

    public void setCloseshift(LocalDateTime closeshift) {
        this.closeshift = closeshift;
    }
}
