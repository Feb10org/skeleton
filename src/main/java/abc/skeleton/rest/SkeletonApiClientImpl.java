package abc.skeleton.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SkeletonApiClientImpl implements SkeletonApiClient {
    private final RestClient restClient;
    private final String baseUrl = "http://localhost:8080/";

    public SkeletonApiClientImpl() {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String getHello(String name) {
        String url = UriComponentsBuilder
                .fromUriString("/hello")
                .queryParam("name", name)
                .build()
                .toUriString();
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
        String url = UriComponentsBuilder
                .fromUriString("/message")
                .build()
                .toUriString();
        Message requestBody = new Message();
        requestBody.setText(text);


        try {

            ResponseEntity<Message> response = restClient.post()
                    .uri(url)
                    .body(requestBody)
                    .retrieve()
                    .toEntity(Message.class);

            return response.getBody();
        } catch (RestClientException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
