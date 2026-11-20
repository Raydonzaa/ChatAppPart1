package com.mycompany.chatapppart1;

import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;

/**
 * Represents one chat message in QuickChat.
 * Holds the recipient, message text, ID and hash, validates the recipient
 * and message length, tracks the messages sent while the program runs,
 * and stores messages in a JSON file.
 */
public class Message {

    // Stores the unique ID of the message
    private String messageID;

    // Stores the recipient's cellphone number
    private String recipient;

    // Stores the message text
    private String messageText;

    // Stores the generated message hash
    private String messageHash;

    // Tracks the number of messages sent
    private static int totalMessagesSent = 0;

    // We use ArrayList<Message> so we store the actual message objects
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();

    // These stay as Strings because they just hold text (the ID and the Hash)
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();

    // Constructor: creates a new message
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;

        // Generate a random 10-digit message ID
        long randomID = ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 10_000_000_000L);

        this.messageID = String.valueOf(randomID);
    }
// Allows the message ID to be set to a known value (used for unit testing)

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    // Checks whether the message ID contains exactly 10 digits
    public boolean checkMessageID() {
        return messageID.matches("[0-9]{10}");
    }

    // Checks whether the recipient's cellphone number is valid
    public String checkRecipientCell() {

        // South African international format: +27 followed by 9 digits
        String cellRegex = "^\\+27[0-9]{9}$";

        if (recipient != null && recipient.matches(cellRegex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does "
                    + "not contain an international code. "
                    + "Please correct the number and try again.";
        }
    }

    // Checks whether the message is within 250 characters
    public String checkMessageLength() {

        if (messageText == null) {
            return "Please enter a message.";
        }

        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extraCharacters = messageText.length() - 250;

            return "Message exceeds 250 characters by "
                    + extraCharacters
                    + "; please reduce the size.";
        }
    }

    // Creates a unique hash for the message
    public String createMessageHash(int messageNumber) {

        String firstTwoDigits = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        messageHash = (firstTwoDigits + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    // Returns the total number of messages successfully sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Allows the user to send, discard or store a message
    public String SentMessage(int choice) {

        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                return "Message successfully stored.";

            default:
                return "Invalid option. Please try again.";
        }
    }

    // Builds the details of this one message
    public String getMessageDetails() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

// Returns the details of all messages sent while the program is running
    public static String printMessages() {

        String allMessages = "";

        for (Message details : sentMessages) {
            allMessages += details.getMessageText() + "\n\n";
        }

        return allMessages;
    }


    /*
 * Stores the message in a JSON file using the Gson library.
 * Reference: Google (n.d.) Gson User Guide.
 * Available at: https://github.com/google/gson/blob/main/UserGuide.md
 * [Accessed 20 October 2026].
     */
    public String storeMessage() {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    File file = new File("storedMessages.json");

    try {
        JsonArray messages = new JsonArray();

        // Load the messages already in the file, if there are any
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                messages = JsonParser.parseReader(reader).getAsJsonArray();
            }
        }

        messages.add(gson.toJsonTree(this));

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(messages, writer);
        }

        return "Message successfully stored.";

    } catch (IOException | IllegalStateException | JsonParseException e) {
        return "Error storing message: " + e.getMessage();
    }
}
    // Getters to access the private data in Part 3
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }
    
    public static ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }
    // This method populates the arrays with the exact test data from the assignment rubric
    public static void populateTestData() {
        // Clear everything first so we don't get duplicates if we run it twice
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        // Test Data Message 1 (Sent)
        Message m1 = new Message("+27834557896", "Did you get the cake?");
        m1.setMessageID("1111111111"); 
        m1.createMessageHash(1);
        m1.SentMessage(1); 
        messageIDs.add(m1.getMessageID());
        messageHashes.add(m1.getMessageHash());

        // Test Data Message 2 (Stored)
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setMessageID("2222222222");
        m2.createMessageHash(2);
        m2.SentMessage(3); // 3 stores it in your logic
        messageIDs.add(m2.getMessageID());
        messageHashes.add(m2.getMessageHash());
        storedMessages.add(m2); 

        // Test Data Message 3 (Disregarded)
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        m3.setMessageID("3333333333");
        m3.createMessageHash(3);
        m3.SentMessage(2); // 2 discards it in your logic
        messageIDs.add(m3.getMessageID());
        messageHashes.add(m3.getMessageHash());
        disregardedMessages.add(m3);

        // Test Data Message 4 (Sent / Stored for testing)
        Message m4 = new Message("0838884567", "It is dinner time !");
        m4.setMessageID("4444444444");
        m4.createMessageHash(4);
        m4.SentMessage(1); // 1 sends it
        messageIDs.add(m4.getMessageID());
        messageHashes.add(m4.getMessageHash());
        storedMessages.add(m4); // Added to stored array for the "longest message" test

        // Test Data Message 5 (Stored)
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.");
        m5.setMessageID("5555555555");
        m5.createMessageHash(5);
        m5.SentMessage(3); // 3 stores it
        messageIDs.add(m5.getMessageID());
        messageHashes.add(m5.getMessageHash());
        storedMessages.add(m5);
    }
}
