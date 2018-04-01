package system.service;

import system.model.User;
import system.service.exception.NotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by vladimir on 19.03.2018.
 */
public interface UserService {
    User save(User user);

    void delete(int id) throws NotFoundException;

    User getByLogin(String login) throws NoSuchElementException;

    List<User> getAll() throws NotFoundException;
}
