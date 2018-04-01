package system.TestData;

import system.model.Role;
import system.model.User;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by vladimir on 28.03.2018.
 */
public class UserServiceData {

    public final static Map<Integer, User> USERS = new HashMap<>();

    public static void init() {
        USERS.put(0, new User(0, LocalDateTime.now(),"Иванов", "Андрей", "Олегович", "admin", "admin", "admin@mail.ru", EnumSet.of(Role.ADMIN)));
        USERS.put(1, new User(1, LocalDateTime.now(),"Иванов", "Антон", "Андреевич", "user", "password", "user@mail.ru", EnumSet.of(Role.USER)));
    }

    public static final User NEWUSER = new User("Иванов", "Артем", "Олегович", "newuser", "password", "newUser@mail.ru", Role.USER);

    public static final User USER_BY_LOGIN = new User(0, LocalDateTime.now(),"Иванов", "Андрей", "Олегович", "admin", "admin", "admin@mail.ru", EnumSet.of(Role.ADMIN));

    public static final User UPDATE_USER = new User(0, LocalDateTime.now(),"ИвановUpdate", "Андрей", "Олегович", "admin", "admin", "admin@mail.ru", EnumSet.of(Role.ADMIN));

    public static Collection<User> getList() {
        return USERS.values();
    }

    public static Collection<User> getUpdateList() {
        USERS.put(0, UPDATE_USER);
        return getList();
    }
}
