package system.service;

import system.model.Client;
import system.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by vladimir on 31.03.2018.
 */
public interface ClientService {
    Client save(Client client);

    void delete(int clientid);

    Client getByNumber(String number) throws NotFoundException;

    List<Client> getAll() throws NotFoundException;

    Client get(int id);
}
