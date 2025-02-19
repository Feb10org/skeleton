package abc.skeleton.testcontainets.integration_tests.fixtures;

import abc.skeleton.testcontainers.model.User;

public class UserFixture {

    public static User createUser() {
        return User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .phone("5444")
                .build();
    }
}
