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
            }
        }
    }
}