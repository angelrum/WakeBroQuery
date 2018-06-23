package system.service;

import system.model.User;
import system.service.exception.NotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by vladimir on 19.03.2018.
 */
public interface UserService extends AbstractService<User> {

    User getByLogin(String login) throws NoSuchElementException;

}
