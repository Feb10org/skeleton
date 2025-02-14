package abc.skeleton.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    private final SkeletonApiClient skeletonApiClient;

    @Autowired
    public HelloController(SkeletonApiClient skeletonApiClient) {
        this.skeletonApiClient = skeletonApiClient;
    }

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
    public ResponseEntity<Message> consumeOwnApi() {
        Message responseMessage = skeletonApiClient.sendMessage("Self-consuming API");
        return ResponseEntity.ok(responseMessage);
    }
}
