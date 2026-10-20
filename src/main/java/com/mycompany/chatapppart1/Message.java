package com.mycompany.chatapppart1;

import java.util.concurrent.ThreadLocalRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    // Stores the details of every message sent while the program runs
    private static ArrayList<String> sentMessages = new ArrayList<>();

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
                sentMessages.add(getMessageDetails());
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

        for (String details : sentMessages) {
            allMessages += details + "\n\n";
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

        java.io.File file = new java.io.File("storedMessages.json");

        try {
            com.google.gson.JsonArray messages = new com.google.gson.JsonArray();

            if (file.exists() && file.length() > 0) {
                try (java.io.FileReader reader
                        = new java.io.FileReader(file)) {
                    messages = com.google.gson.JsonParser
                            .parseReader(reader).getAsJsonArray();
                }
            }

            messages.add(gson.toJsonTree(this));

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(messages, writer);
            }

            return "Message successfully stored.";

        } catch (IOException | IllegalStateException
                | com.google.gson.JsonParseException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

}
