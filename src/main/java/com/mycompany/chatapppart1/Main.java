package com.mycompany.chatapppart1;

import java.util.Scanner;

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
                
                boolean loginSuccessful =
                        user.loginUser(enteredUsername, enteredPassword);
                
                System.out.println(
                        user.returnLoginStatus(loginSuccessful)
                );
                
                
            if (loginSuccessful) {

                System.out.println("\nWelcome to QuickChat");

            int menuChoice = 0;

            while (menuChoice != 3) {

                System.out.println("\n===== QUICKCHAT MENU =====");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");

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
            int messageNumber = message.returnTotalMessages() + 1;
            message.createMessageHash(messageNumber);

            System.out.println(message.SentMessage(1));
            System.out.println(message.printMessages());
            break;

            case "2":
            System.out.println("Message discarded.");
            break;

            case "3":
            System.out.println(message.storeMessage());
            break;

        default:
            System.out.println("Invalid option. Please try again.");
    }

} else {
    System.out.println("Please correct your message before continuing.");
}

            break;
                
            case 2:
                System.out.println("Coming Soon.");
                break;

            case 3:
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
}