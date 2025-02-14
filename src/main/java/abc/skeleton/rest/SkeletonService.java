package abc.skeleton.rest;

import org.springframework.stereotype.Service;

@Service
public class SkeletonService {
    private final SkeletonApiClient skeletonApiClient;

    public SkeletonService(SkeletonApiClient skeletonApiClient) {
        this.skeletonApiClient = skeletonApiClient;
    }

    public String getHello(String text) {
        return skeletonApiClient.getHello("Ben");
    }

    public Message sendMessage(String text) {
        return skeletonApiClient.sendMessage("Send");
    }
}
