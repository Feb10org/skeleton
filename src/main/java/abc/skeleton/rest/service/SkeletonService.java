package abc.skeleton.rest.service;

import abc.skeleton.rest.api.SkeletonApiClient;
import abc.skeleton.rest.model.Message;
import org.springframework.stereotype.Service;

@Service
public class SkeletonService {
    private final SkeletonApiClient skeletonApiClient;

    public SkeletonService(SkeletonApiClient skeletonApiClient) {
        this.skeletonApiClient = skeletonApiClient;
    }

    public String getHelloMessage(String name) {
        return skeletonApiClient.getHello(name);
    }

    public Message processMessage(String input) {
        return skeletonApiClient.sendMessage("Processed: " + input);
    }
}
