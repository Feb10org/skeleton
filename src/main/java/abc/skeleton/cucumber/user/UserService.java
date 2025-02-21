package abc.skeleton.cucumber.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new UserException("User already exists", HttpStatus.CONFLICT);
        });
        User user = new User(username, password);
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("No user with username: " + username, HttpStatus.NOT_FOUND));
    }
}
