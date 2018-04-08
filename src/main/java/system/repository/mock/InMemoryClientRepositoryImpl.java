package system.repository.mock;

import org.springframework.stereotype.Repository;
import system.model.Client;
import system.repository.ClientRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 18.03.2018.
 */
@Repository
public class InMemoryClientRepositoryImpl implements ClientRepository {
    private Map<Integer, Client> clients = new HashMap<>();
    private int id = 0;

    @PostConstruct
    public void init() {
        List<Client> list = Arrays.asList(
                new Client("Иван", "Иванов", "Иванович", "+7(911)111-11-11", "Анапа"),
                new Client("Антон", "Иванов", "Иванович", "+7(911)111-11-12", "Новороссийск")
        );

        list.forEach(this::save);
    }

    @Override
    public Client save(Client client) {
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
                .findFirst()
                .get();
    }
}