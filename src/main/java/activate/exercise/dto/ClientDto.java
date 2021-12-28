package activate.exercise.dto;

import activate.exercise.model.Client;
import lombok.Data;

import java.util.Date;

@Data
public class ClientDto {
    private Long id;
    private String name;
    private Date created;

    public Client toClient() {
        Client client = new Client();
        client.setName(name);

        return client;
    }

    public static ClientDto fromClient(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setCreated(client.getCreated());

        return clientDto;
    }
}
