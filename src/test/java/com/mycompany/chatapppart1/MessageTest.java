package com.mycompany.chatapppart1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MessageTest {

    // Test Case 1: checks the message hash is built correctly
    @Test
    public void testMessageHashMessage1() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        // Fixed ID so the first two digits are known
        message.setMessageID("0012345678");

        assertEquals("00:0:HITONIGHT", message.createMessageHash(0));
    }
        // Message of 250 characters or less should be accepted
    @Test
    public void testMessageLengthSuccess() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    // Message over 250 characters should report how many characters too many
    @Test
    public void testMessageLengthFailure() {

        // Builds a message that is 260 characters long (10 over the limit)
        String longMessage = "a".repeat(260);

        Message message = new Message("+27718693002", longMessage);

        assertEquals(
                "Message exceeds 250 characters by 10; please reduce the size.",
                message.checkMessageLength()
        );
    }
}