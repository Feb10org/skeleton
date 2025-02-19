package abc.skeleton.rest_client.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {
    private static final String X_CORRELATION_ID = "X-Correlation-ID";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
            headers.add(X_CORRELATION_ID, UUID.randomUUID().toString());
            // fixme
//        if (!headers.containsKey(X_CORRELATION_ID)) {
//        } else {
//            List<String> corId = headers.get(X_CORRELATION_ID);
//            corId.add("_new_" + ThreadLocalRandom.current().nextInt());
//            headers.put(X_CORRELATION_ID, corId);
//        }
        return execution.execute(request, body);
    }
}
