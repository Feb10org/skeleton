package abc.skeleton.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProcessController.class)
class ProcessControllerTest {

    @Mock
    private SkeletonApiClientImpl skeletonApiClientImpl;

    @InjectMocks
    private ProcessController processController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testProcess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/process"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Received: Send"));
    }
}
