package abc.skeleton.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {
    private final ApiClient apiClient;

    public ProcessController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("/process")
    public Message process() {
        String response = apiClient.getHello("SpringBootUser");

        return apiClient.sendMessage("Processing: " + response);
    }
}
