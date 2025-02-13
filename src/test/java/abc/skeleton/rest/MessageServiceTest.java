package abc.skeleton.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testGetHelloMessage() {
        when(apiClient.getHello("TestUser")).thenReturn("Hello, TestUser!");
        String result = messageService.getHelloMessage("TestUser");
        assertEquals("Hello, TestUser!", result);
    }

    @Test
    void testProcessMessage() {
        Message mockMessage = new Message("Processed: Test Message");
        when(apiClient.sendMessage("Processed: Test Message")).thenReturn(mockMessage);

        Message result = messageService.processMessage("Test Message");
        assertNotNull(result);
        assertEquals("Processed: Test Message", result.getText());
    }
}
