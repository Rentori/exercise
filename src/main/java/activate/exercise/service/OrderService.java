package activate.exercise.service;

import activate.exercise.model.Client;
import activate.exercise.model.Order;

import java.util.List;

public interface OrderService {
    Order getById(Long id);

    Order save(Order order, Client client);

    List<Order> getAll();

    void deleteOrder(Order order);

    Order updateOrderPrice(Order order);
}
