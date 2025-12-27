package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCommunicationLayer {

    @Test
    void getInstance() {

        CommunicationLayer instance = CommunicationLayer.getInstance();
        assertNotNull(instance);

        CommunicationLayer.reset();

        CommunicationLayer newInstance = CommunicationLayer.getInstance();
        assertNotNull(newInstance);
        assertNotEquals(instance, newInstance);

        CommunicationLayer newInstance2 = CommunicationLayer.getInstance();
        assertEquals(newInstance, newInstance2);
    }
}