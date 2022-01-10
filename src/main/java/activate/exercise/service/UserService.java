package activate.exercise.service;

import activate.exercise.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User save(User user);

    User findByUsername(String username);

    User findById(Long id);

    void deleteUser(User user);

    User updateUsername(User user);

    User updateUserEmail(User user);
}
