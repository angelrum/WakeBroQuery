package system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Регистрация открытия и закрытия смены пользователем
 * @user идентификатор пользователя (user)
 */
@NamedQueries({
        @NamedQuery(name = Register.DELETE, query = "DELETE FROM Register as r WHERE r.id =: id"),
        @NamedQuery(name = Register.ALL, query = "SELECT r FROM Register as r"),
        @NamedQuery(name = Register.ALL_BY_USER, query = "SELECT r FROM Register as r WHERE r.user.id =: user_id")
})
@Entity
@Table(name = "register")
public class Register extends AbstractBaseEntity {

    public static final String DELETE = "Register.delete";
    public static final String ALL = "Register.all";
    public static final String ALL_BY_USER = "Register.all_by_user";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "open_shift")
    @NotNull
    private LocalDateTime openshift;

    @Column(name = "close_shift")
    private LocalDateTime closeshift;

    public Register() {
        this.openshift = LocalDateTime.now();
    }

    public void setCloseshift(LocalDateTime closeshift) {
        this.closeshift = closeshift;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Register{" +
                "user=" + user +
                ", openshift=" + openshift +
                ", closeshift=" + closeshift +
                '}';
    }
}
