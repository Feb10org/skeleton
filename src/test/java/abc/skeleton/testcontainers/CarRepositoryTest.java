package abc.skeleton.testcontainers;

import abc.skeleton.testcontainers.config.EntityManagerFactoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Testcontainers
@Import(EntityManagerFactoryConfig.class)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine").withDatabaseName("test-db");


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void whenCallingSave_thenEntityIsPersistedToDb() {
        Car car = new Car("Toyota");
        carRepository.save(car);
        List<Car> cars = carRepository.findAll();
        assertThat(cars)
                .hasSize(3)
                .extracting(Car::getBrand)
                .containsExactlyInAnyOrder("Mercedes", "Toyota", "BMW");
    }
}