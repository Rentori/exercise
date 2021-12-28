package activate.exercise.service.impl;

import activate.exercise.model.Client;
import activate.exercise.model.Order;
import activate.exercise.repository.OrderRepository;
import activate.exercise.service.ClientService;
import activate.exercise.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientService clientService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
    }

    @Override
    public Order getById(Long id) {
        Order order = orderRepository.getById(id);
        log.info("IN getById - order: {} found by id", order);

        return order;
    }

    @Override
    public Order save(Order order, Client client) {
        order.setClient(client);
        log.info("IN save - order: {} successfully saved", order);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        log.info("IN getAll: successfully get");

        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
        log.info("IN deleteOrder - order with id: {} successfully deleted", order.getId());
    }

    @Override
    public Order updateOrderPrice(Order order) {
        Order updatedOrder = orderRepository.getById(order.getId());
        updatedOrder.setPrice(order.getPrice());
        log.info("IN updateOrderPrice - order: {} successfully update", order);

        return orderRepository.save(updatedOrder);
    }
}
