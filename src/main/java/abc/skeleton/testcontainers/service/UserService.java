package abc.skeleton.testcontainers.service;

import abc.skeleton.testcontainers.model.User;
import abc.skeleton.testcontainers.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("user not found for id:" + id)
                );
    }

    public User addUser(User user) {
        return repository.save(user);
    }
}
