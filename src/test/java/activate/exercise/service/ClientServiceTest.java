package activate.exercise.service;

import activate.exercise.model.Client;
import activate.exercise.model.User;
import activate.exercise.repository.ClientRepository;
import activate.exercise.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    private final List<Client> clientList = Collections.singletonList(getClient());

    @InjectMocks
    private ClientServiceImpl clientServiceTest;

    @Mock
    private ClientRepository clientRepositoryTest;

    @Test
    void getByIdTest() {
        when(clientRepositoryTest.getById(anyLong())).thenReturn(getClient());
        clientServiceTest.getById(1L);
        verify(clientRepositoryTest, times(1)).getById(1L);
    }

    @Test
    void saveTest() {
        when(clientRepositoryTest.save(any())).thenReturn(getClient());
        clientServiceTest.save(getClient(), getUser());
        verify(clientRepositoryTest, times(1)).save(getClient());
    }

    @Test
    void getAllTest() {
        when(clientRepositoryTest.findAll()).thenReturn(clientList);
        assertNotNull(clientServiceTest.getAll());

        verify(clientRepositoryTest, times(1)).findAll();
    }

    @Test
    void deleteClientTest() {
        doNothing().when(clientRepositoryTest).delete(getClient());
        clientServiceTest.deleteClient(getClient());
        verify(clientRepositoryTest, times(1)).delete(getClient());
    }

    @Test
    void updateClientNameTest() {
        when(clientRepositoryTest.getById(anyLong())).thenReturn(getClient());
        when(clientRepositoryTest.save(any())).thenReturn(getClient());
        clientServiceTest.updateClientName(getClient());
        assertNotNull(clientRepositoryTest.getById(1L));
        verify(clientRepositoryTest, times(1)).save(getClient());
    }

    private Client getClient() {
        Client client = new Client();
        client.setName("test");
        client.setCreated(new Date());
        client.setId(1L);

        return client;
    }

    private User getUser() {
        User user = new User();
        user.setEmail("testEmail");
        user.setUsername("test");
        user.setId(1L);

        return user;
    }
}
