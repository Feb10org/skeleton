package abc.skeleton.rest;

public interface SkeletonApiClient {
    String getHello(String name);

    Message sendMessage(String text);
}
