package activate.exercise.controller;

import activate.exercise.dto.AuthenticationRequestDto;
import activate.exercise.dto.UserDto;
import activate.exercise.model.User;
import activate.exercise.security.jwt.JwtTokenProvider;
import activate.exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider,
                                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto requestDto) throws AuthenticationException {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            String role = user.getRoles().toString();
            String returnRole;

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            if (role.contains("ROLE_ADMIN")) {
                returnRole = "ADMIN";
            } else {
                returnRole = "USER";
            }

            String token = jwtTokenProvider.createToken(username, new ArrayList<>(user.getRoles()));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("role", returnRole);
            return ok(model);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping(value = "signUp")
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        User user = userDto.toUser();
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        try {
            userService.register(user);
        } catch (Exception e) {
            e.getStackTrace();
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }
}
