package activate.exercise.service;

import activate.exercise.model.Client;
import activate.exercise.model.Order;
import activate.exercise.repository.OrderRepository;
import activate.exercise.service.impl.OrderServiceImpl;
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
class OrderServicelTest {
    private final List<Order> orderList = Collections.singletonList(getOrder());

    @InjectMocks
    private OrderServiceImpl orderServiceTest;

    @Mock
    private OrderRepository orderRepositoryTest;

    @Test
    void getByIdTest() {
        when(orderRepositoryTest.getById(anyLong())).thenReturn(getOrder());
        orderServiceTest.getById(1L);
        verify(orderRepositoryTest, times(1)).getById(1L);
    }

    @Test
    void saveTest() {
        when(orderRepositoryTest.save(any())).thenReturn(getOrder());
        orderServiceTest.save(getOrder(), getClient());
        verify(orderRepositoryTest, times(1)).save(getOrder());
    }

    @Test
    void getAllTest() {
        when(orderRepositoryTest.findAll()).thenReturn(orderList);
        assertNotNull(orderServiceTest.getAll());

        verify(orderRepositoryTest, times(1)).findAll();
    }

    @Test
    void deleteOrder() {
        doNothing().when(orderRepositoryTest).delete(getOrder());
        orderServiceTest.deleteOrder(getOrder());
        verify(orderRepositoryTest, times(1)).delete(getOrder());
    }

    @Test
    void updateOrderPriceTest() {
        when(orderRepositoryTest.getById(anyLong())).thenReturn(getOrder());
        when(orderRepositoryTest.save(any())).thenReturn(getOrder());
        orderServiceTest.updateOrderPrice(getOrder());
        assertNotNull(orderRepositoryTest.getById(1L));
        verify(orderRepositoryTest, times(1)).save(getOrder());
    }

    private Order getOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setPrice(1000L);

        return order;
    }

    private Client getClient() {
        Client client = new Client();
        client.setName("test");
        client.setCreated(new Date());
        client.setId(1L);

        return client;
    }
}
