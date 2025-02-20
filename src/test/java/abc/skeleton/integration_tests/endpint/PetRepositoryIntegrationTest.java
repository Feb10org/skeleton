package abc.skeleton.integration_tests.endpint;

import abc.skeleton.rest_client.service.PetService;
import com.example.model.PetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("integrationtest")
public class PetRepositoryIntegrationTest {

    @Container
    public static MSSQLServerContainer<?> sqlServer =
            new MSSQLServerContainer<>("mcr.microsoft.com/azure-sql-edge:latest")
                    .acceptLicense();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlServer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlServer::getUsername);
        registry.add("spring.datasource.password", sqlServer::getPassword);
    }

    @Autowired
    private PetService petService;

    @Test
    void testSaveAndFindPet() {
        PetDto testPet = petService.addPet("Testowy Pet");

        PetDto foundPet = petService.getPet(testPet.getId());
        assertNotNull(foundPet);
        assertEquals("Testowy Pet", foundPet.getName());
    }
}
