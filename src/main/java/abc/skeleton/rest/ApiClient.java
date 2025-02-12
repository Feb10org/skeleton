package abc.skeleton.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiClient {
    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8080/";

    public ApiClient() {
        this.webClient = WebClient.create(baseUrl);
    }

    public String getHello(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/hello")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Czekaj na odpowiedź synchronizując wywołanie
    }

    public Message sendMessage(String text) {
        Message request = new Message();
        request.setText(text);

        return webClient.post()
                .uri("/message")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Message.class)
                .block(); // Czekaj na odpowiedź synchronizując wywołanie
    }
}
