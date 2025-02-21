package abc.skeleton.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testProcess() throws Exception {

        mockMvc.perform(get("/process"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Received: Processed: Processing: Hello, SpringBootUser!"));
    }
}
