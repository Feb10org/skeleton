package abc.skeleton.integration_tests.database;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class DbContainerTest {
    @Container
    private static final MSSQLServerContainer<?> sqlServer = new MSSQLServerContainer<>();
    //"mcr.microsoft.com/azure-sql-edge:latest"
//            .withUsername("skeleton")
//            .withPassword("skele@Ton123")
//            .withInitScript("init.sql");

    private static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = DriverManager.getConnection(sqlServer.getJdbcUrl(), sqlServer.getUsername(), sqlServer.getPassword());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) connection.close();
    }

    @Test
    void testInsertUser() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            int rows = stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('test_user', 'test@example.com');");
            assertEquals(1, rows);
        }
    }

    @Test
    void testInsertOrderWithValidUser() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('order_user', 'order@example.com');");
            int rows = stmt.executeUpdate("INSERT INTO Orders (user_id, total_amount) VALUES ((SELECT id FROM Users WHERE email = 'order@example.com'), 150.0);");
            assertEquals(1, rows);
        }
    }

    @Test
    void testForeignKeyConstraint() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            assertThrows(SQLException.class, () ->
                    stmt.executeUpdate("INSERT INTO Orders (user_id, total_amount) VALUES (999, 100.0);")
            );
        }
    }

    @Test
    void testEmailUniqueness() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('unique_user', 'unique@example.com');");
            assertThrows(SQLException.class, () ->
                    stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('another_user', 'unique@example.com');")
            );
        }
    }
}
