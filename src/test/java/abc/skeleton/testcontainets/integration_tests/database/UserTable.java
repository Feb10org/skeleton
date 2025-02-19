package abc.skeleton.testcontainets.integration_tests.database;

import abc.skeleton.testcontainers.model.User;
import abc.skeleton.testcontainers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTable {
    @Autowired
    private UserRepository repository;

    public void clear() {
        repository.deleteAll();
    }

    public void givenContains(User... users) {
        repository.saveAll(List.of(users));
    }
}

