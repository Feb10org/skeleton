package abc.skeleton.rest;

import org.springframework.stereotype.Service;

@Service
public class SkeletonService {
    private final SkeletonApiClientImpl skeletonApiClientImpl;

    public SkeletonService(SkeletonApiClientImpl skeletonApiClientImpl) {
        this.skeletonApiClientImpl = skeletonApiClientImpl;
    }

    public String getHello(String text) {
        return skeletonApiClientImpl.getHello("Ben");
    }

    public Message sendMessage(String text) {
        return skeletonApiClientImpl.sendMessage("Send");
    }
}
