package activate.exercise.dto;

import activate.exercise.model.Order;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private String clientName;
    private Long price;
    private Date created;

    public Order toOrder() {
        Order order = new Order();
        order.setPrice(price);
        order.setCreated(new Date());

        return order;
    }

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setCreated(order.getCreated());
        orderDto.setClientName(order.getClient().getName());

        return orderDto;
    }

    public String getClientName() {
        return clientName;
    }
}
