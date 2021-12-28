package activate.exercise.service.impl;

import activate.exercise.model.Role;
import activate.exercise.model.User;
import activate.exercise.repository.RoleRepository;
import activate.exercise.repository.UserRepository;
import activate.exercise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setCreated(new Date());
        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        log.info("IN getAll: successfully get");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        log.info("IN save - user: {} successfully saved", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username", username);

        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.getById(id);
        log.info("IN findById - user: {} found by id", user.getUsername());
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("IN deleteById - user with id: {} successfully deleted", id);
    }

    @Override
    public User updateUsername(User user) {
        User updatedUser = userRepository.getById(user.getId());
        updatedUser.setUsername(user.getUsername());
        log.info("IN updateUsername - user: {} successfully update", user.getUsername());

        return userRepository.save(updatedUser);

    }

    @Override
    public User updateUserEmail(User user) {
        User updatedUser = userRepository.getById(user.getId());
        updatedUser.setEmail(user.getEmail());
        log.info("IN updateUserEmail - user email: {} successfully update", user.getEmail());

        return userRepository.save(updatedUser);
    }
}
