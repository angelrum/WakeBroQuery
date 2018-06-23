package system.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import system.model.Role;
import system.model.User;
import system.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 18.03.2018.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    public static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> users = new HashMap<>();
    private int id = 0;


    public void init() {
        log.info("+++ PostConstruct +++");
        List<User> list = Arrays.asList(
                new User("Иванов", "Андрей", "Олегович", "+79111111111", "admin", "admin", "admin@mail.ru", Role.ADMIN),
                new User("Иванов", "Антон", "Андреевич", "+79111111111", "user", "password", "user@mail.ru", Role.USER)
        );
        list.forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (checkIsNew(user)) {
            user.setId(id++);
            users.put(user.getId(), user);
            return user;
        }
        return users.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        return users.remove(id)!=null;
    }

    @Override
    public List<User> getAll() {
        return users.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public User getByLogin(String login) throws NoSuchElementException{
        return users.values()
                .stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst().orElse(null);
    }
}
