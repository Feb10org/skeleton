package abc.skeleton.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        String responseMessage = "Hello, " + name + "!";
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/message")
    public ResponseEntity<Message> receiveMessage(@RequestBody Message message) {
        message.setText("Received: " + message.getText());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/consume")
    public ResponseEntity<Message> consumeOwnApi(ApiClient apiClient) {
        Message responseMessage = apiClient.sendMessage("Self-consuming API");
        return ResponseEntity.ok(responseMessage);
    }
}
