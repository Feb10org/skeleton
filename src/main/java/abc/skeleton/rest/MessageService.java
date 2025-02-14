package abc.skeleton.rest;


import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final SkeletonApiClientImpl skeletonApiClientImpl;

    public MessageService(SkeletonApiClientImpl skeletonApiClientImpl) {
        this.skeletonApiClientImpl = skeletonApiClientImpl;
    }

    public String getHelloMessage(String name) {
        return skeletonApiClientImpl.getHello(name);
    }

    public Message processMessage(String input) {
        return skeletonApiClientImpl.sendMessage("Processed: " + input);
    }
}
