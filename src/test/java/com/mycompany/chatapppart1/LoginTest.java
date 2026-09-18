package com.mycompany.chatapppart1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void testCheckUserNameCorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyle!!!!!!",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityCorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityIncorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "password", "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberCorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberIncorrect() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        user.registerUser();

        boolean result = user.loginUser(
                "kyl_1",
                "Ch&&sec@ke99!"
        );

        assertTrue(result);
    }

    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        user.registerUser();

        boolean result = user.loginUser(
                "wrong",
                "wrong"
        );

        assertFalse(result);
    }

    @Test
    public void testSuccessfulRegistration() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertEquals(
                "User successfully registered.",
                user.registerUser()
        );
    }

    @Test
    public void testInvalidUsernameMessage() {

        Login user = new Login(
                "Raydon", "Example", "kyle!!!!!!",
                "Ch&&sec@ke99!", "+27838968976"
        );

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser()
        );
    }

    @Test
    public void testInvalidPasswordMessage() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "password", "+27838968976"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.registerUser()
        );
    }

    @Test
    public void testInvalidCellPhoneMessage() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "08966553"
        );

        assertEquals(
                "Cell phone number incorrectly formatted or does not contain international code.",
                user.registerUser()
        );
    }

    @Test
    public void testSuccessfulLoginMessage() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        user.registerUser();

        assertEquals(
                "Welcome Raydon, Example it is great to see you again.",
                user.returnLoginStatus(true)
        );
    }

    @Test
    public void testFailedLoginMessage() {

        Login user = new Login(
                "Raydon", "Example", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        user.registerUser();

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus(false)
        );
    }
}
