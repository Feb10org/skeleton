package abc.skeleton.integration_tests.endpint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PetstoreIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreatePetSuccessfully() throws Exception {
        String petJson = """
            {
                "id": 1,
                "name": "Buddy",
                "photoUrls": ["http://example.com/dog.jpg"],
                "status": "available"
            }
            """;

        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.status").value("available"));
    }

    @Test
    void shouldFindPetById() throws Exception {
        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnNotFoundForNonExistingPet() throws Exception {
        mockMvc.perform(get("/api/v3/pet/999"))
                .andExpect(status().isNotFound());
    }
}
