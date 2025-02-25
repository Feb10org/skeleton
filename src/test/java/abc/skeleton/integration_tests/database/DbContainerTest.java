package abc.skeleton.integration_tests.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class DbContainerTest {

    @Container
    private static final MSSQLServerContainer<?> sqlServer = new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:latest")
            .acceptLicense()
            .withInitScript("init.sql");


    @DynamicPropertySource
    static void configureDatabaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlServer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlServer::getUsername);
        registry.add("spring.datasource.password", sqlServer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    @BeforeAll
    static void setUp() {
        assertThat(sqlServer.isRunning()).isTrue();
    }

    @Test
    void testConnection() throws SQLException {
        try (Connection connection = sqlServer.createConnection("")) {
            assertThat(connection.isValid(1)).isTrue();
        }
    }

    @Test
    void testUserTableExistence() throws SQLException {
        try (Connection connection = sqlServer.createConnection("");
             var statement = connection.prepareStatement(
                     "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'users'");
             var resultSet = statement.executeQuery()) {

            assertThat(connection.isValid(1)).isTrue();

            boolean tableExists = resultSet.next();
            assertThat(tableExists).isTrue();
        }
    }

}
