package abc.skeleton.rest;


import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final SkeletonApiClient skeletonApiClient;

    public MessageService(SkeletonApiClient skeletonApiClient) {
        this.skeletonApiClient = skeletonApiClient;
    }

    public String getHelloMessage(String name) {
        return skeletonApiClient.getHello(name);
    }

    public Message processMessage(String input) {
        return skeletonApiClient.sendMessage("Processed: " + input);
    }
}
