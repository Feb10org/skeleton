package abc.skeleton.rest;

import abc.skeleton.rest.api.SkeletonApiClient;
import abc.skeleton.rest.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Disabled
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SkeletonApiClient skeletonApiClient;

    @Test
    void testSayHello() throws Exception {
        mockMvc.perform(get("/hello?name=Alice"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Alice!"));
    }

    @Test
    void testSayHelloDefault() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }

    // as this test is testing self consuming api, you must run application when check if it is working.
    @Test
    void testConsume() throws Exception {
        mockMvc.perform(get("/consume"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"text\":\"Received: Self-consuming API\"}"));
    }

    @Test
    void testReceiveMessage() throws Exception {
        Message message = new Message("Test message");
        String jsonRequest = objectMapper.writeValueAsString(message);

        mockMvc.perform(post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"Received: Test message\"}"));
    }
}
