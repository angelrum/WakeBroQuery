package system.repository;

import system.model.Client;

import java.util.List;

/**
 * Created by vladimir on 18.03.2018.
 */
public interface ClientRepository {

    Client save(Client client);

    boolean delete(int id);

    Client get(int id);

    Client getByNumber(String number);

    List<Client> getAll();
}
