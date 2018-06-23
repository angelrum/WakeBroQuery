package system.Service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import system.model.Role;
import system.model.User;
import system.service.UserService;

import static system.TestData.UserServiceData.*;
import static system.TestData.ServiceUtil.*;

/**
 * Created by vladimir on 29.04.2018.
 */

public class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void save() {
        User user = service.save(NEWUSER);
        assertMatch(user, NEWUSER);
    }

    @Test
    public void get() {
        User user = service.getByLogin(ADMIN.getLogin());
        assertMatch(user, ADMIN);
    }
    @Test
    public void update() {
        User user = service.getByLogin(ADMIN.getLogin());
        user.setLogin(UPDATE_ADMIN.getLogin());
        service.save(user);
        assertMatch(service.getByLogin(UPDATE_ADMIN.getLogin()), UPDATE_ADMIN);
    }

    @Test
    public void delete() {
        User user = service.getByLogin(ADMIN.getLogin());
        service.delete(user.getId());
        assertMatch(service.getAll(), USER);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), userList);
    }

    @Test
    public void role() {
        User user = service.getByLogin(ADMIN.getLogin());
        assertMatchIgnorFields(user.getRole(), Role.ADMIN);
    }

}
