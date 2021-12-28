package activate.exercise.controller;

import activate.exercise.dto.ClientDto;
import activate.exercise.dto.OrderDto;
import activate.exercise.dto.UserDto;
import activate.exercise.model.Client;
import activate.exercise.model.Order;
import activate.exercise.model.User;
import activate.exercise.security.jwt.JwtTokenProvider;
import activate.exercise.service.impl.ClientServiceImpl;
import activate.exercise.service.impl.OrderServiceImpl;
import activate.exercise.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/update")
public class UpdateRestControllerV1 {
    private UserServiceImpl userService;
    private OrderServiceImpl orderService;
    private ClientServiceImpl clientService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UpdateRestControllerV1(UserServiceImpl userService, OrderServiceImpl orderService, ClientServiceImpl clientService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("clients")
    public ResponseEntity<ClientDto> updateClientName(@RequestBody ClientDto clientDto,
                                                      @RequestHeader("Authorization") String token) {
        Client client = userService.findByUsername(jwtTokenProvider.getUsername(token.substring(7))).getClient();

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        client.setName(clientDto.getName());
        clientService.updateClientName(client);

        return ResponseEntity.ok(ClientDto.fromClient(client));
    }

    @PostMapping("orders")
    public ResponseEntity<OrderDto> updateOrderPriceById(@RequestBody OrderDto orderDto) {
        Order order = orderService.getById(orderDto.getId());
        if(order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order newOrder = orderService.updateOrderPrice(order);

        return ResponseEntity.ok(OrderDto.fromOrder(newOrder));
    }

    @PostMapping("users/username")
    public ResponseEntity<UserDto> updateUsername(@RequestBody UserDto userDto,
                                                  @RequestHeader("Authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsername(token.substring(7)));
        user.setUsername(userDto.getUsername());
        userService.updateUsername(user);

        return ResponseEntity.ok(UserDto.fromUser(user));
    }

    @PostMapping("users/email")
    public ResponseEntity<UserDto> updateUserEmail(@RequestBody UserDto userDto,
                                                  @RequestHeader("Authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsername(token.substring(7)));
        user.setEmail(userDto.getEmail());
        userService.updateUserEmail(user);

        return ResponseEntity.ok(UserDto.fromUser(user));
    }
}
