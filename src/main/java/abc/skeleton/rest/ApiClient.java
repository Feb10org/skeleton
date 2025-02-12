package abc.skeleton.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import abc.skeleton.rest.Message;

@Component
public class ApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/";

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public String getHello(String name) {
        String url = baseUrl + "/hello?name=" + name;
        return restTemplate.getForObject(url, String.class);
    }

    public Message sendMessage(String text) {
        String url = baseUrl + "/message";
        Message request = new Message();
        request.setText(text);
        ResponseEntity<Message> response = restTemplate.postForEntity(url, request, Message.class);
        return response.getBody();
    }
}
