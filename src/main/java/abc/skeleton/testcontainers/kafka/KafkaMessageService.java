package abc.skeleton.testcontainers.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaMessageService {

    private final List<String> messages = new CopyOnWriteArrayList<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        kafkaTemplate.send("test-topic", message);
        kafkaTemplate.flush();
    }

    @KafkaListener(topics = "test-topic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Message received: " + message);
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
