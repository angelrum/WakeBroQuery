package system.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by vladimir on 17.03.2018.
 * Информация о пользователе
 */
@NamedQueries({
        @NamedQuery(name = User.BY_LOGIN,   query = "SELECT u FROM User u WHERE u.login =:login and u.enabled = true"),
        @NamedQuery(name = User.DELETE,     query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_ALL,    query = "SELECT u FROM User u ORDER BY u.login ASC")
})
@Entity
@Table(name = "user")
public class User extends AbstractNamedEntity {
    public static final String BY_LOGIN = "User.byLogin";
    public static final String DELETE   = "User.delete";
    public static final String GET_ALL = "User.get_all";

    @Column(name = "login")
    @Size(min = 5, max = 20)
    private String login;

    @Column(name = "password")
    @Size(min = 5, max = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "enabled")
    private boolean enabled = true;

    public User(String firstname, String lastname, String secondname, String telnumber, String login, String password, String email, Role role) {
        super(firstname, lastname, secondname, telnumber);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(Integer id, LocalDateTime creation, String firstname, String lastname, String secondname, String telnumber, String login, String password, String email, Role role) {
        super(id, firstname, lastname, secondname, creation, telnumber);
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;

        if (enabled != user.enabled) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
