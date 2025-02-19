package abc.skeleton.testcontainers.kafka;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


@SpringBootTest
@Testcontainers
public class KafkaMessageServiceTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.0"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private KafkaMessageService kafkaMessageService;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Test
    public void testSendAndReceiveMessage() {

        await().atMost(10, TimeUnit.SECONDS)
                .until(() ->
                        kafkaListenerEndpointRegistry.getListenerContainers().stream()
                                .allMatch(container -> container.isRunning() &&
                                        container.getAssignedPartitions() != null &&
                                        !container.getAssignedPartitions().isEmpty())
                );

        String testMessage = "Test Kafka Message";
        kafkaMessageService.sendMessage(testMessage);

        await().atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(kafkaMessageService.getMessages()).contains("Test Kafka Message"));
    }
}
