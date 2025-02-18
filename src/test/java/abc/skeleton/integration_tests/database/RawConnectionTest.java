package abc.skeleton.integration_tests.database;
import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class RawConnectionTest {
    private static Connection connection;
    private static final String URL = "jdbc:sqlserver://localhost:1433;database=skeleton_db;trustServerCertificate=true;";
    private static final String USER = "skeleton";
    private static final String PASSWORD = "skele@Ton123";

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS Orders;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Users;");
            stmt.executeUpdate("CREATE TABLE Users (id INT PRIMARY KEY IDENTITY, username NVARCHAR(50), email NVARCHAR(100) UNIQUE);");
            stmt.executeUpdate("CREATE TABLE Orders (id INT PRIMARY KEY IDENTITY, user_id INT, total_amount FLOAT, FOREIGN KEY(user_id) REFERENCES Users(id));");
        }
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
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