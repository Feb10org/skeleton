package abc.skeleton.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
class HelloController {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8080/";

    public HelloController() {
        this.webClient = WebClient.create(baseUrl);
    }

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
        String url = baseUrl + "message";

        Message request = new Message();
        request.setText("Self-consuming API");

        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Message.class)
                .block(); // Synchronizowanie odpowiedzi
    }
}
