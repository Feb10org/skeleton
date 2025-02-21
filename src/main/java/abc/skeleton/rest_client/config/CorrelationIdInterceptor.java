package abc.skeleton.rest_client.config;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static abc.skeleton.logback.RequestHandler.TRACE_ID;
import static abc.skeleton.logback.RequestHandler.TRACE_ID_HEADER;

public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (MDC.get(TRACE_ID) != null && !MDC.get(TRACE_ID).isBlank()) {
            HttpHeaders headers = request.getHeaders();
            headers.add(TRACE_ID_HEADER, MDC.get(TRACE_ID));
        }

        return execution.execute(request, body);
    }
}
