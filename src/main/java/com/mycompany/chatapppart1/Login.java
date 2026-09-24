package com.mycompany.chatapppart1;

public class Login {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String cellPhoneNumber;

    private String registeredUsername;
    private String registeredPassword;

    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
// Checks whether the username contains an underscore
// and is no more than five characters long.
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }
// Checks whether the password meets all required
// password complexity rules.
    public boolean checkPasswordComplexity() {

        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecialCharacter =
                password.matches(".*[^a-zA-Z0-9].*");

        return password.length() >= 8
                && hasCapitalLetter
                && hasNumber
                && hasSpecialCharacter;
    }
// Validates that the cellphone number uses the
// required South African international format.
    public boolean checkCellPhoneNumber() {

       /*
 * Cell phone number validation uses Java regular expressions.
 * Source: Oracle (2026), Java String.matches() documentation.
 * https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/String.html
 */
        String cellPhoneRegex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber.matches(cellPhoneRegex);
    }
// Validates the user's registration details and
// stores the username and password if registration is successful.
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        registeredUsername = username;
        registeredPassword = password;

        return "User successfully registered.";
    }
// Checks whether the entered login details match
// the username and password stored during registration.
    public boolean loginUser(String enteredUsername,
                             String enteredPassword) {

        return enteredUsername.equals(registeredUsername)
                && enteredPassword.equals(registeredPassword);
    }
// Returns the appropriate message depending on
// whether the login was successful or unsuccessful.
    public String returnLoginStatus(boolean loginSuccessful) {

        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}