package abc.skeleton.testcontainets.integration_tests.config;

import abc.skeleton.testcontainets.integration_tests.database.DataBaseConfig;
import abc.skeleton.testcontainets.integration_tests.database.UserTable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("IT")
@ContextConfiguration(classes = BaseIntegrationTest.IntegrationTestConfig.class)
public abstract class BaseIntegrationTest extends DataBaseConfig {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected UserTable userTable;

    @BeforeEach
    void cleanAll() {
        userTable.clear();
    }

    @TestConfiguration
    static class IntegrationTestConfig {
        @Bean
        public UserTable userTable() {
            return new UserTable();
        }
    }

}
