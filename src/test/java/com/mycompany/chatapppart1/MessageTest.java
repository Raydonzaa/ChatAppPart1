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
    // Recipient number with international code should be accepted

    @Test
    public void testRecipientCellSuccess() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Recipient number without international code should be rejected
    @Test
    public void testRecipientCellFailure() {

        Message message = new Message(
                "08575975889",
                "Hi Kegan, did you receive the payment?"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }
    // Choice 1: user selects Send Message

    @Test
    public void testSentMessageSend() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals("Message successfully sent.", message.SentMessage(1));
    }

    // Choice 2: user selects Disregard Message
    @Test
    public void testSentMessageDiscard() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals("Press 0 to delete the message.", message.SentMessage(2));
    }

    // Choice 3: user selects Store Message
    @Test
    public void testSentMessageStore() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals("Message successfully stored.", message.SentMessage(3));
    }
    // A freshly generated message ID should be exactly 10 digits

    @Test
    public void testMessageIDGenerated() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
    }

    // A message ID that is not 10 digits should be rejected
    @Test
    public void testMessageIDInvalid() {

        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        message.setMessageID("12345");

        assertFalse(message.checkMessageID());
    }
    // Tests the hash for every test message in a loop

    @Test
    public void testMessageHashesInLoop() {

        String[] recipients = {"+27718693002", "08575975889"};

        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Kegan, did you receive the payment?"
        };

        String[] expectedHashes = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {

            Message message = new Message(recipients[i], messages[i]);

            // Fixed ID so the first two digits are known
            message.setMessageID("0012345678");

            assertEquals(expectedHashes[i], message.createMessageHash(i));
        }
    }
    // ==========================================
    // PART 3 TESTS: Array logic and Searching
    // ==========================================

    @Test
    public void testSentMessagesArray() {
        // Load the specific test data for Part 3
        Message.populateTestData(); 
        
        // The rubric expects 2 sent messages from the test data
        assertEquals(2, Message.sentMessages.size(), "There should be 2 sent messages.");
        assertEquals("Did you get the cake?", Message.sentMessages.get(0).getMessageText());
    }

    @Test
    public void testLongestMessage() {
        Message.populateTestData(); 
        
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        String actualLongest = "";
        
        for (Message m : Message.getStoredMessages()) {
            if (m.getMessageText().length() > actualLongest.length()) {
                actualLongest = m.getMessageText();
            }
        }
        
        assertEquals(expectedLongest, actualLongest, "The longest message was not found correctly.");
    }

    @Test
    public void testSearchMessageID() {
        Message.populateTestData(); 
        
        String searchID = "4444444444"; // We set this in populateTestData
        String expectedMessage = "It is dinner time !";
        String actualMessage = "";

        for (Message m : Message.getStoredMessages()) {
            if (m.getMessageID().equals(searchID)) {
                actualMessage = m.getMessageText();
                break;
            }
        }
        
        assertEquals(expectedMessage, actualMessage, "Failed to find message by ID.");
    }

    @Test
    public void testSearchRecipient() {
        Message.populateTestData(); 
        
        String searchRecipient = "+27838884567";
        String expectedMsg1 = "Where are you? You are late! I have asked you to be on time.";
        String expectedMsg2 = "Ok, I am leaving without you.";
        
        StringBuilder foundMessages = new StringBuilder();
        
        for (Message m : Message.getStoredMessages()) {
            if (m.getRecipient().equals(searchRecipient)) {
                foundMessages.append(m.getMessageText()).append(" ");
            }
        }
        
        assertTrue(foundMessages.toString().contains(expectedMsg1), "Did not find first message.");
        assertTrue(foundMessages.toString().contains(expectedMsg2), "Did not find second message.");
    }

    @Test
    public void testDeleteMessageByHash() {
        Message.populateTestData(); 
        
        String hashToDelete = Message.getStoredMessages().get(0).getMessageHash(); 
        int initialSize = Message.getStoredMessages().size();
        
        for (int i = 0; i < Message.getStoredMessages().size(); i++) {
            if (Message.getStoredMessages().get(i).getMessageHash().equals(hashToDelete)) {
                Message.getStoredMessages().remove(i);
                break;
            }
        }
        
        assertEquals(initialSize - 1, Message.getStoredMessages().size(), "Message was not deleted.");
    }
}
