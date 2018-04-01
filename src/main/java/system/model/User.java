package system.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by vladimir on 17.03.2018.
 * Информация о пользователе
 */
public class User extends AbstractNamedEntity {

    private String login;

    private String password;

    private Set<Role> role;

    private String email;

    private boolean enabled = true;

    public User(String firstname, String lastname, String secondname, String login, String password, String email, Role role, Role... roles) {
        super(firstname, lastname, secondname);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = EnumSet.of(role, roles);
    }

    public User(Integer id, LocalDateTime creation, String firstname, String lastname, String secondname, String login, String password, String email, Set<Role> role) {
        super(id, firstname, lastname, secondname, creation);
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRole() {
        return role;
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

    public void setRole(Set<Role> role) {
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
