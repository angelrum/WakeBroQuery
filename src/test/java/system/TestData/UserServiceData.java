package system.TestData;

import system.model.Role;
import system.model.User;

import java.util.*;

import static system.TestData.ServiceUtil.*;

/**
 * Created by vladimir on 28.03.2018.
 */
public class UserServiceData {
    public static final int ADMIN_ID = 100000;
    public static final User ADMIN = new User("Иван", "Иванов", "Иванович", "admin", "admin", "admin@mail.ru", Role.ADMIN);
    public static final User USER = new User("Антон", "Сидоров", "Антонович", "test", "test", "test@mail.ru", Role.USER);
    public static final User NEWUSER = new User("Иванов", "Артем", "Олегович", "newuser", "password", "newUser@mail.ru", Role.USER);
    public static final User UPDATE_ADMIN = new User("Иван", "Иванов", "Иванович", "updateUser", "admin", "admin@mail.ru", Role.ADMIN);

    public static List<User> userList = Arrays.asList(ADMIN, USER);

    static {
        Collections.sort(userList, (o1, o2) -> o1.getLogin().compareTo(o2.getLogin()));
    }
    public static void assertMatch(User actual, User expected) {
        assertMatchIgnorFields(actual, expected, "id", "create");
    }

    public static void assertMatch(Iterable<User> actual, User...users) {
        assertMatch(actual, Arrays.asList(users));
    }

    public static void assertMatch(Iterable<User> actual, List<User> expected) {
        assertMatchIgnorFields(actual, expected, "id", "create");
    }

}
