package activate.exercise.service;

import activate.exercise.model.User;
import activate.exercise.repository.RoleRepository;
import activate.exercise.repository.UserRepository;
import activate.exercise.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private final List<User> userList = Collections.singletonList(getUser());

    @InjectMocks
    private UserServiceImpl userServiceTest;

    @Mock
    private UserRepository userRepositoryTest;

    @Mock
    private RoleRepository roleRepositoryTest;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void registerTest() {
        User userAfterSignUp = new User();
        userAfterSignUp.setUsername("testUsername");
        userAfterSignUp.setPassword("testPassword");
        userAfterSignUp.setEmail("testEmail");
        userAfterSignUp.setCreated(new Date());
        userAfterSignUp.setId(1L);

        when(userRepositoryTest.save(any(User.class))).thenReturn(userAfterSignUp);

        User userFromTest = userServiceTest.register(userAfterSignUp);

        assertThat(userAfterSignUp, instanceOf(User.class));
        assertNotNull(userAfterSignUp.getCreated());
        assertSame(userAfterSignUp, userFromTest);

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleRepositoryTest, times(1)).findByName(anyString());
        verify(userRepositoryTest, times(1)).save(userAfterSignUp);
    }

    @Test
    void getAllTest() {
        when(userRepositoryTest.findAll()).thenReturn(userList);
        List<User> userListUnderTest = userServiceTest.getAll();

        assertNotNull(userListUnderTest);
        verify(userRepositoryTest, times(1)).findAll();
    }

    @Test
    void saveTest() {
        userServiceTest.save(getUser());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepositoryTest, times(1)).save(any(User.class));
    }

    @Test
    void findByUsernameTest() {
        when(userRepositoryTest.findByUsername(anyString())).thenReturn(getUser());
        userServiceTest.findByUsername("testUsername");
        verify(userRepositoryTest, times(1)).findByUsername(anyString());
    }

    @Test
    void findByIdTest() {
        when(userRepositoryTest.getById(anyLong())).thenReturn(getUser());
        userServiceTest.findById(1L);
        verify(userRepositoryTest, times(1)).getById(1L);
    }

    @Test
    void updateUsernameTest() {
        when(userRepositoryTest.getById(anyLong())).thenReturn(getUser());
        when(userRepositoryTest.save(any())).thenReturn(getUser());
        userServiceTest.updateUsername(getUser());
        assertNotNull(userRepositoryTest.getById(1L));
        verify(userRepositoryTest, times(1)).save(getUser());
    }

    @Test
    void updateUserEmailTest() {
        when(userRepositoryTest.getById(anyLong())).thenReturn(getUser());
        when(userRepositoryTest.save(any())).thenReturn(getUser());
        userServiceTest.updateUserEmail(getUser());
        assertNotNull(userRepositoryTest.getById(1L));
        verify(userRepositoryTest, times(1)).save(getUser());
    }

    private User getUser() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setEmail("testEmail");
        user.setCreated(new Date());
        user.setId(1L);

        return user;
    }
}
