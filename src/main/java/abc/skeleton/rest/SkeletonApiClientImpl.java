package abc.skeleton.rest;

public interface SkeletonApiClientImpl {
    String getHello(String name);

    Message sendMessage(String text);
}
