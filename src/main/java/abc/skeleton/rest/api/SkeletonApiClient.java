package abc.skeleton.rest.api;

import abc.skeleton.rest.model.Message;

public interface SkeletonApiClient {
    String getHello(String name);

    Message sendMessage(String text);
}
