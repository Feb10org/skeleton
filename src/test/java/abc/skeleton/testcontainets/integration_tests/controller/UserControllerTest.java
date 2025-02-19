package abc.skeleton.testcontainets.integration_tests.controller;


import abc.skeleton.testcontainets.integration_tests.config.BaseIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static abc.skeleton.testcontainets.integration_tests.fixtures.UserFixture.createUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    void post_whenUserNotExist_shouldCreateUser() {
        var user = createUser();
        var json = mapper.writeValueAsString(user);
        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.phone").exists());
    }

    @Test
    @SneakyThrows
    void get_whenUserExists_shouldReturnUser() {
        var user = createUser();
        userTable.givenContains(user);

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));

    }
}
