package abc.skeleton.cucumber.user;

import org.springframework.beans.factory.annotation.Autowired;
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
            throw new RuntimeException("User already exists");
        });
        User user = new User(username, password);
        return userRepository.save(user);
    }
}
