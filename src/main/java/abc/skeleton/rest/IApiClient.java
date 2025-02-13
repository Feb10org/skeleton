package abc.skeleton.rest;

public interface IApiClient {
    String getHello(String name);

    Message sendMessage(String text);
}
