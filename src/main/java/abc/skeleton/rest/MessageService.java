package abc.skeleton.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private ApiClient apiClient;

    public MessageService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String getHelloMessage(String name) {
        return apiClient.getHello(name);
    }

    public Message processMessage(String input) {
        return apiClient.sendMessage("Processed: " + input);
    }
}
