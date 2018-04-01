package system.service;

import org.junit.jupiter.api.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.model.User;
import system.service.exception.NotFoundException;
import system.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static system.TestData.UserServiceData.*;

/**
 * Created by vladimir on 28.03.2018.
 */
class UserServiceImplTest {

    private static UserService service;
    private static ConfigurableApplicationContext appCtx;

    @BeforeEach
    public void beforeEach() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        service = appCtx.getBean(UserServiceImpl.class);
        init();
    }

    @AfterEach
    public void afterEach() {
        appCtx.close();
    }

    @Test
    void save() {
        User user = service.save(NEWUSER);
        NEWUSER.setId(user.getId());
        assertEquals(service.getByLogin(NEWUSER.getLogin()), NEWUSER);
    }

    @Test
    void update() {
        service.equals(UPDATE_USER);
        assertIterableEquals(getUpdateList(), service.getAll());
    }

    @Test
    void delete() {
        service.delete(0);
        USERS.remove(0);
        assertIterableEquals(getList(), service.getAll());
    }
    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, ()->service.delete(10));
    }

    @Test
    void getByLoginNotFound () {
        assertThrows(NoSuchElementException.class, ()->service.getByLogin("angel"));
    }

    @Test
    void getByLogin() {
        User user = service.getByLogin("admin");
        assertEquals(USER_BY_LOGIN, user);
    }

    @Test
    void getAll() {
        assertIterableEquals(getList(), service.getAll());
    }

}