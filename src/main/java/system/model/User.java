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
        @NamedQuery(name = User.BY_LOGIN,   query = "SELECT u FROM User u INNER JOIN FETCH u.roles WHERE u.login =:login"),
        @NamedQuery(name = User.DELETE,     query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_ALL,    query = "SELECT u FROM User u INNER JOIN FETCH u.roles ORDER BY u.login ASC")
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
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "enabled")
    private boolean enabled = true;

    public User(String firstname, String lastname, String secondname, String login, String password, String email, Role role, Role... roles) {
        super(firstname, lastname, secondname);
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = EnumSet.of(role, roles);
    }

    public User(Integer id, LocalDateTime creation, String firstname, String lastname, String secondname, String login, String password, String email, Set<Role> roles) {
        super(id, firstname, lastname, secondname, creation);
        this.login = login;
        this.password = password;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
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

    public void setRoles(Set<Role> role) {
        this.roles = role;
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
                ", role=" + roles +
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
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
