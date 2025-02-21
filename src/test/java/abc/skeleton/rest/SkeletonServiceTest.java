package abc.skeleton.rest;

import abc.skeleton.rest.api.SkeletonApiClient;
import abc.skeleton.rest.model.Message;
import abc.skeleton.rest.service.SkeletonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkeletonServiceTest {

    @Mock
    private SkeletonApiClient skeletonApiClient;

    @InjectMocks
    private SkeletonService skeletonService;

    @Test
    void testGetHelloMessage() {
        when(skeletonApiClient.getHello("TestUser")).thenReturn("Hello, TestUser!");
        String result = skeletonService.getHelloMessage("TestUser");
        assertEquals("Hello, TestUser!", result);
    }

    @Test
    void testProcessMessage() {
        Message mockMessage = new Message("Processed: Test Message");
        when(skeletonApiClient.sendMessage("Processed: Test Message")).thenReturn(mockMessage);

        Message result = skeletonService.processMessage("Test Message");
        assertNotNull(result);
        assertEquals("Processed: Test Message", result.getText());
    }
}
