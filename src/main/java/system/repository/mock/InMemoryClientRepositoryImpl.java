package system.repository.mock;

import org.springframework.stereotype.Repository;
import system.controller.AuthorizedUser;
import system.model.Client;
import system.repository.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 18.03.2018.
 */
@Repository
public class InMemoryClientRepositoryImpl implements ClientRepository {
    private Map<Integer, Client> clients = new HashMap<>();
    private int id = 1;

    public void init() {
        List<Client> list = Arrays.asList(
                //new Client("Иван", "Иванов", "Иванович", "+7(911)111-11-11", "Анапа", AuthorizedUser.id()),
                //new Client("Антон", "Иванов", "Иванович", "+7(911)111-11-12", "Новороссийск", AuthorizedUser.id())
        );

        //list.forEach(this::save);
    }

    @Override
    public Client save(Client client, int userId) {
        if (client.isNew())
            client.setId(id++);
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public boolean delete(int id) {
        return clients.remove(id)!=null;
    }

    @Override
    public Client get(int id) {
        return clients.get(id);
    }

    @Override
    public List<Client> getAll() {
        return clients.values()
                .stream()
                .collect(Collectors.toList());
    }

    public Client getByNumber(String number) {
        return clients.values()
                .stream()
                .filter(client -> number.equals(client.getTelnumber()))
                .findFirst().orElse(null);
    }
}
