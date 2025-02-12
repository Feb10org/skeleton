package abc.skeleton.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@RestController
class HelloController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/message")
    public Message receiveMessage(@RequestBody Message message) {
        message.setText("Received: " + message.getText());
        return message;
    }

    @GetMapping("/consume")
    public Message consumeOwnApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/message";

        Message request = new Message();
        request.setText("Self-consuming API");

        ResponseEntity<Message> response = restTemplate.postForEntity(url, request, Message.class);

        return response.getBody();
    }
}

class Message {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
