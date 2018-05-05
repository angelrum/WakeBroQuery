package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.model.User;
import system.repository.UserRepository;
import system.service.UserService;
import system.service.exception.NotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 27.03.2018.
 */

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public User save(User user) {
        checkNotNull(user);
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User getByLogin(String login) throws NotFoundException {
        return checkNotFound(repository.getByLogin(login), "login=" + login);
    }

    @Override
    public List<User> getAll() throws NotFoundException {
        return checkNullOrEmptyList(repository.getAll());
    }
}
