package models.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.Icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageModelTest {

    private MessageModel testMessage;

    @BeforeEach
    void setupMessage() {
        testMessage = new MessageModel("Title", "Description", "Date", Icon.APPICON);
    }

    @Test
    void getTitle() {
        assertEquals("Title", testMessage.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("Description", testMessage.getDescription());
    }

    @Test
    void getIcon() {
        assertEquals(Icon.APPICON, testMessage.getIcon());
    }

    @Test
    void getDate() {
        assertEquals("Date", testMessage.getDate());
    }

    @Test
    void setTitle() {
        testMessage.setTitle("Title2");
        assertEquals("Title2", testMessage.getTitle());
    }

    @Test
    void setDescription() {
        testMessage.setDescription("Description2");
        assertEquals("Description2", testMessage.getDescription());
    }

    @Test
    void setIcon() {
        testMessage.setIcon(Icon.NOTIFICATION);
        assertEquals(Icon.NOTIFICATION, testMessage.getIcon());
    }

    @Test
    void setDate() {
        testMessage.setDate("Date2");
        assertEquals("Date2", testMessage.getDate());
    }
}