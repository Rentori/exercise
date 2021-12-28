package activate.exercise.controller;

import activate.exercise.dto.ClientDto;
import activate.exercise.dto.OrderDto;
import activate.exercise.dto.UserDto;
import activate.exercise.model.Client;
import activate.exercise.model.Order;
import activate.exercise.model.User;
import activate.exercise.security.jwt.JwtTokenProvider;
import activate.exercise.service.ClientService;
import activate.exercise.service.OrderService;
import activate.exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/data/")
public class DataRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public DataRestControllerV1(UserService userService,
                                JwtTokenProvider jwtTokenProvider,
                                ClientService clientService,
                                OrderService orderService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.clientService = clientService;
        this.orderService = orderService;
    }

    @PostMapping("orders")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,
                                                @RequestHeader("Authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsername(token.substring(7)));
        Order order = orderDto.toOrder();
        Client client = new Client();
        client.setName(orderDto.getClientName());

        if (user.getClient() == null) {
            client = clientService.save(client, user);
            user.setClient(client);
        }
        order = orderService.save(order, user.getClient());

        return new ResponseEntity<>(OrderDto.fromOrder(order), HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        userService.getAll().forEach(user -> {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setCreated(user.getCreated());
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDtoList.add(userDto);
        });
        return ResponseEntity.ok(userDtoList);
    }


    @GetMapping("orders")
    public ResponseEntity<Set<OrderDto>> getClientOrders(@RequestHeader("Authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsername(token.substring(7)));
        Client client = user.getClient();
        Set<OrderDto> orders = new HashSet<>();

        client.getOrders().forEach(order -> {
            OrderDto orderDto = OrderDto.fromOrder(order);
            orders.add(orderDto);
        });

        return ResponseEntity.ok(orders);
    }

    @GetMapping("orders/clients/{id}")
    public ResponseEntity<Set<OrderDto>> getOrdersByClientId(@PathVariable Long id) {
        Client client = clientService.getById(id);

        Set<OrderDto> orders = new HashSet<>();

        client.getOrders().forEach(order -> {
            OrderDto orderDto = OrderDto.fromOrder(order);
            orders.add(orderDto);
        });

        return ResponseEntity.ok(orders);
    }

    @GetMapping("clients/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        Client client = clientService.getById(id);

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(ClientDto.fromClient(client));
    }

    @GetMapping("clients")
    public ResponseEntity<Set<ClientDto>> getClient() {
        Set<ClientDto> clients = new HashSet<>();
        clientService.getAll().forEach(client -> {
            ClientDto clientDto = ClientDto.fromClient(client);
            clients.add(clientDto);
        });

        return ResponseEntity.ok(clients);
    }
}
