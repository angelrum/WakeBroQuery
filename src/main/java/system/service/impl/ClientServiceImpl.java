package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.controller.AuthorizedUser;
import system.model.Client;
import system.repository.ClientRepository;
import system.service.ClientService;
import system.service.exception.NotFoundException;

import java.util.List;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 31.03.2018.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client client) {
        checkNotNull(client);
        return repository.save(client, AuthorizedUser.id());
    }

    @Override
    public void delete(int clientid) {
        checkNotFoundWithId(repository.delete(clientid), clientid);
    }

    @Override
    public Client getByNumber(String number) {
        return checkNotFound(repository.getByNumber(number), "number=" + number);
    }

    @Override
    public List<Client> getAll() throws NotFoundException {
        return checkNullOrEmptyList(repository.getAll());
    }

    @Override
    public Client get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }
}
