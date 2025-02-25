package abc.skeleton.integration_tests.database;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBDockerComposeContainerTest {
    private static final Logger log = LoggerFactory.getLogger(DBDockerComposeContainerTest.class);
    private static Connection connection;
    private static final String DOCKER_ENV_LOCATION = "sql/";
    private static final String DOCKER_COMPOSE_FILE = "sql/docker-compose.yml";

    private static final Dotenv dotenv = Dotenv.configure().directory(DOCKER_ENV_LOCATION).load();
    private static final String USER = dotenv.get("DB_USERNAME");
    private static final String PASSWORD = dotenv.get("DB_USER_PWD");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String SERVICE_NAME = dotenv.get("SERVICE_NAME");

    private static final Integer SQLSERVER_PORT = Integer.valueOf(dotenv.get("SQLSERVER_PORT"));

    static DockerComposeContainer<?> compose;

    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        File dockerComposeFile = new ClassPathResource(DOCKER_COMPOSE_FILE).getFile();

        compose = new DockerComposeContainer<>(dockerComposeFile).withExposedService(SERVICE_NAME, SQLSERVER_PORT, Wait.forLogMessage(".*SQL Server is now ready for client connections.*\\n", 1))
                .waitingFor(SERVICE_NAME, Wait.forListeningPort())
                .withStartupTimeout(Duration.ofMinutes(5));
        compose.start();

        addShutdownHook();

        final String JDBC_URL = String.format("jdbc:sqlserver://localhost:%d;database=%s;trustServerCertificate=true;", SQLSERVER_PORT, DB_NAME);
        obtainConnection(JDBC_URL);

    }

    @BeforeEach
    void resetDatabase() throws SQLException {
        dropTables(connection);
        createTables(connection);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error( "Error closing database connection: ", e);
        } finally {
            if (compose != null) {
                try {
                    compose.stop();
                } catch (Exception e) {
                    log.error("Error stopping Docker container", e);
                }
            }
        }
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
            assertThrows(SQLException.class, () -> stmt.executeUpdate("INSERT INTO Orders (user_id, total_amount) VALUES (999, 100.0);"));
        }
    }

    @Test
    void testEmailUniqueness() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('unique_user', 'unique@example.com');");
            assertThrows(SQLException.class, () -> stmt.executeUpdate("INSERT INTO Users (username, email) VALUES ('another_user', 'unique@example.com');"));
            stmt.executeUpdate("DELETE FROM Users WHERE email = 'unique@example.com'");
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Users (id INT PRIMARY KEY IDENTITY, username NVARCHAR(50), email NVARCHAR(100) UNIQUE);");
            stmt.executeUpdate("CREATE TABLE Orders (id INT PRIMARY KEY IDENTITY, user_id INT, total_amount FLOAT, FOREIGN KEY(user_id) REFERENCES Users(id));");
        }
    }

    private static void dropTables(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS Orders;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Users;");
        }
    }

    private static void waitingForDbInit(long timeoutInSec) throws InterruptedException {
        final long timeout = System.currentTimeMillis() + timeoutInSec * 1000L;

        while (System.currentTimeMillis() < timeout) {
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT name FROM sys.databases WHERE name = '" + DB_NAME + "'")) {
                if (rs.next()) {
                    log.info("Database is ready.");
                    return;
                }
            } catch (SQLException e) {
                log.info("Waiting for database...");
            }
            Thread.sleep(5000);
        }
        throw new RuntimeException("Database did not become ready in time");
    }

    private static void obtainConnection(String JDBC_URL2) throws InterruptedException {
        int attempts = 3;
        while (attempts > 0) {
            try {
                connection = DriverManager.getConnection(JDBC_URL2, USER, PASSWORD);
                waitingForDbInit(60);
                break;
            } catch (SQLException e) {
                log.error("Failed to connect to the database: ", e);
                attempts--;
                if (attempts == 0) {
                    throw new RuntimeException("Unable to establish database connection", e);
                }
                Thread.sleep(5000);
            }
        }
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook triggered, stopping containers...");
            if (compose != null) {
                compose.stop();
            }
        }));
    }
}