package abc.skeleton.rest_client.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static java.util.UUID.randomUUID;

@Component
@Slf4j
public class RequestHandler implements HandlerInterceptor {

    static final String TRACE_ID_HEADER = "X-Trace-Id";
    static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = randomUUID().toString();
        }
        MDC.put(TRACE_ID, traceId);

        log.info("handle request before goes to controller");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("go out from controller");
        MDC.remove(TRACE_ID);
    }
}
