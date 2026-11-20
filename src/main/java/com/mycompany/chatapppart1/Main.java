package com.mycompany.chatapppart1;

import java.util.Scanner;

/**
 * Console entry point for QuickChat.
 * Registers and logs in a user, then lets the logged-in user send,
 * discard or store the number of messages they chose.
 */
public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("===== CHAT APP REGISTRATION =====");

            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            System.out.print("Enter your South African cellphone number: ");
            String cellPhoneNumber = scanner.nextLine();

            Login user = new Login(
                    firstName,
                    lastName,
                    username,
                    password,
                    cellPhoneNumber
            );

            System.out.println();
            System.out.println(user.registerUser());

            if (user.checkUserName()
                    && user.checkPasswordComplexity()
                    && user.checkCellPhoneNumber()) {

                System.out.println();
                System.out.println("===== LOGIN =====");

                System.out.print("Enter username: ");
                String enteredUsername = scanner.nextLine();

                System.out.print("Enter password: ");
                String enteredPassword = scanner.nextLine();

                boolean loginSuccessful
                        = user.loginUser(enteredUsername, enteredPassword);

                System.out.println(
                        user.returnLoginStatus(loginSuccessful)
                );

                if (loginSuccessful) {

                    System.out.println("\nWelcome to QuickChat.");

                    int numberOfMessages = 0;

// Keeps asking until the user enters a whole number of 1 or more
                    while (numberOfMessages < 1) {
                        System.out.print("How many messages do you wish to enter? ");

                        try {
                            numberOfMessages = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            numberOfMessages = 0;
                        }

                        if (numberOfMessages < 1) {
                            System.out.println("Please enter a whole number of 1 or more.");
                        }
                    }

                    int menuChoice = 0;

                    while (menuChoice != 5) {

                        System.out.println("\n===== QUICKCHAT MENU =====");
                        System.out.println("1) Send Messages");
                        System.out.println("2) Show recently sent messages");
                        System.out.println("3) Stored Messages");
                        System.out.println("4) Quit");

                        System.out.print("Choose an option: ");
                        String input = scanner.nextLine();

                        try {
                            menuChoice = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a number.");
                            continue;
                        }

                        switch (menuChoice) {
                            case 1:
                                for (int i = 0; i < numberOfMessages; i++) {
                                    System.out.println("\n===== SEND MESSAGE =====");

                                    System.out.print("Enter recipient cellphone number: ");
                                    String recipient = scanner.nextLine();

                                    System.out.print("Enter your message: ");
                                    String messageText = scanner.nextLine();

                                    Message message = new Message(recipient, messageText);

                                    String cellResult = message.checkRecipientCell();
                                    String lengthResult = message.checkMessageLength();

                                    System.out.println(cellResult);
                                    System.out.println(lengthResult);

// Only continue if the recipient and message are valid
                                    if (cellResult.equals("Cell phone number successfully captured.")
                                            && lengthResult.equals("Message ready to send.")) {

                                        System.out.println("\nWhat would you like to do?");
                                        System.out.println("1) Send Message");
                                        System.out.println("2) Discard Message");
                                        System.out.println("3) Store Message");

                                        System.out.print("Choose an option: ");
                                        String actionInput = scanner.nextLine();

                                        switch (actionInput) {
                                            case "1":
                                                int messageNumber = i;
                                                message.createMessageHash(messageNumber);

                                                System.out.println(message.SentMessage(1));
                                                System.out.println(message.getMessageDetails());
                                                break;

                                            case "2":
                                                System.out.println(message.SentMessage(2));
                                                System.out.print("Enter 0 to delete the message: ");
                                                String deleteInput = scanner.nextLine();

                                                if (deleteInput.equals("0")) {
                                                    System.out.println("Message deleted.");
                                                }
                                                break;

                                            case "3":
                                                message.createMessageHash(i);
                                                System.out.println(message.storeMessage());
                                                break;

                                            default:
                                                System.out.println("Invalid option. Please try again.");
                                        }

                                    } else {
                                        System.out.println("Please correct your message before continuing.");
                                    }
                                }
                                System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
                                break;

                            case 2:
                                System.out.println("Coming Soon.");
                                break;
                            case 3:
                                // This will call the new method we are about to create
                                displayStoredMessagesMenu(scanner);
                                break;

                            case 4:
                                System.out.println("Goodbye!");
                                break;

                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                }

            }
        }
    }
    public static void displayStoredMessagesMenu(Scanner scanner) {
        // We call populateTestData here just for now so we have data to look at
        Message.populateTestData(); 
        
        int choice = 0;
        while (choice != 7) {
            System.out.println("\n--- STORED MESSAGES MENU ---");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages by recipient");
            System.out.println("5) Delete a message using message hash");
            System.out.println("6) Display full report");
            System.out.println("7) Back to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- SENDERS AND RECIPIENTS ---");
                    for (Message m : Message.getStoredMessages()) {
                        System.out.println("Recipient: " + m.getRecipient() + " | Sender: You");
                    }
                    break;
                case 2:
                    System.out.println("\n--- LONGEST MESSAGE ---");
                    String longest = "";
                    for (Message m : Message.getStoredMessages()) {
                        if (m.getMessageText().length() > longest.length()) {
                            longest = m.getMessageText();
                        }
                    }
                    System.out.println(longest);
                    break;
                case 3:
                    System.out.println("\n--- SEARCH BY ID ---");
                    System.out.print("Enter Message ID to search: ");
                    String searchID = scanner.nextLine();
                    boolean foundID = false;
                    for (Message m : Message.getStoredMessages()) {
                        if (m.getMessageID().equals(searchID)) {
                            System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getMessageText());
                            foundID = true;
                        }
                    }
                    if (!foundID) System.out.println("Message ID not found.");
                    break;
                case 4:
                    System.out.println("\n--- SEARCH BY RECIPIENT ---");
                    System.out.print("Enter recipient number to search: ");
                    String searchRecipient = scanner.nextLine();
                    for (Message m : Message.getStoredMessages()) {
                        if (m.getRecipient().equals(searchRecipient)) {
                            System.out.println("Message: " + m.getMessageText());
                        }
                    }
                    break;
                case 5:
                    System.out.println("\n--- DELETE MESSAGE ---");
                    System.out.print("Enter Message Hash to delete: ");
                    String searchHash = scanner.nextLine();
                    boolean deleted = false;
                    for (int i = 0; i < Message.getStoredMessages().size(); i++) {
                        if (Message.getStoredMessages().get(i).getMessageHash().equals(searchHash)) {
                            Message.getStoredMessages().remove(i);
                            System.out.println("Message successfully deleted.");
                            deleted = true;
                            break;
                        }
                    }
                    if (!deleted) System.out.println("Hash not found.");
                    break;
                case 6:
                    System.out.println("\n--- FULL REPORT ---");
                    for (Message m : Message.getStoredMessages()) {
                        System.out.println("Hash: " + m.getMessageHash() + 
                                           " | Recipient: " + m.getRecipient() + 
                                           " | Message: " + m.getMessageText());
                    }
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}

