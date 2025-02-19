package abc.skeleton.integration_tests.endpint;


import abc.skeleton.rest.controller.PetController;
import abc.skeleton.rest.service.PetService;
import com.example.model.PetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;



import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PetController petController;

    @Autowired
    private PetService petService;

    @Test
    void contextLoads() {
        assertThat(petController).isNotNull();
        assertThat(petService).isNotNull();
    }

    @Test
    void shouldReturnPetById() {
        PetDto petDto = new PetDto();
        String url = "http://localhost:" + port + "/pet/1";
        PetDto response = restTemplate.getForObject(url, PetDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Whiskers");
    }

    @Test
    void shouldAddNewPet() {
        PetController.CreatePetDto newPet = new PetController.CreatePetDto("Fluffy");

        PetDto response = restTemplate.postForObject("http://localhost:" + port + "/pet", newPet, PetDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Fluffy");
    }
}

