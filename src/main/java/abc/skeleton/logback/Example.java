package abc.skeleton.logback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class Example {

    @RequestMapping("/login")
    public void hello() {
        MDC.put("userId", "12345");
        MDC.put("requestId", "req-42132132");

        log.info("User login");

        MDC.clear();
    }
}
