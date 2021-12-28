package activate.exercise.service.impl;

import activate.exercise.model.Client;
import activate.exercise.model.User;
import activate.exercise.repository.ClientRepository;
import activate.exercise.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.getById(id);
    }

    @Override
    public Client save(Client client, User user) {
        client.setUser(user);
        client.setCreated(new Date());
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public Client updateClientName(Client client) {
        Client updatedClient = clientRepository.getById(client.getId());
        updatedClient.setName(client.getName());

        return clientRepository.save(updatedClient);
    }
}
