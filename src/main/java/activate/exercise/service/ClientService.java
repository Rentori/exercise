package activate.exercise.service;

import activate.exercise.model.Client;
import activate.exercise.model.User;

import java.util.List;

public interface ClientService {
    Client getById(Long id);

    Client save(Client client, User user);

    List<Client> getAll();

    void deleteClient(Client client);

    Client updateClientName(Client client);
}
