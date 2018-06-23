package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.controller.AuthorizedUser;
import system.model.Register;
import system.repository.RegisterRepository;
import system.service.RegisterService;
import static system.util.ValidationUtil.*;
import java.util.List;

@Service
public class RegisterSeviceImpl implements RegisterService {

    @Autowired
    private RegisterRepository repository;

    @Override
    public Register save(Register register) throws IllegalArgumentException {
        return checkNotNull(repository.save(register, AuthorizedUser.id()));
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Register get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Register> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Register> getAll(int userId) {
        return repository.getAll(userId);
    }
}
