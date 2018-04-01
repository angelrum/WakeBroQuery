package system.repository;

import system.model.User;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by vladimir on 18.03.2018.
 */
public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    List<User> getAll();

    User getByLogin(String login) throws NoSuchElementException;
}
