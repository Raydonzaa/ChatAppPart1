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
}