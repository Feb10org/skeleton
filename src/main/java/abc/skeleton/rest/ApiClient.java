package abc.skeleton.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ApiClient implements IApiClient {
    private final RestClient restClient;
    private final String baseUrl = "http://localhost:8080/";


    public ApiClient() {
        this.restClient = RestClient.create();
    }

    @Override
    public String getHello(String name) {
        String url = baseUrl + "/hello?name=" + name;
        try {
            ResponseEntity<String> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(String.class);

            return response.getBody();
        } catch (RestClientException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public Message sendMessage(String text) {
        String url = baseUrl + "/message";
        Message request = new Message();
        request.setText(text);

        try {

            ResponseEntity<Message> response = restClient.post()
                    .uri(url)
                    .body(request)
                    .retrieve()
                    .toEntity(Message.class);

            return response.getBody();
        } catch (RestClientException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
