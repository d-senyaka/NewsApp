package user_management;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {

    @Test
    public void testValidUserDetails() {
        UserValidator.validate("ValidUser", "user@example.com", "Password123", "Password123");
        System.out.println("Test Valid Details: PASSED");
    }

    @Test
    public void testInvalidUsername() {
        try {
            UserValidator.validate("ab", "user@example.com", "Password123", "Password123");
            fail("Expected IllegalArgumentException for invalid username, but none was thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test Invalid User Name: PASSED");
        }
    }

    @Test
    public void testInvalidEmail_one() {
        try {
            UserValidator.validate("ValidUser", "userexample.com", "Password123", "Password123");
            fail("Expected IllegalArgumentException for invalid email (missing '@'), but none was thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test Invalid Email one: PASSED");
        }
    }

    @Test
    public void testInvalidEmail_two() {
        try {
            UserValidator.validate("ValidUser", "userexamplecom", "Password123", "Password123");
            fail("Expected IllegalArgumentException for invalid email (missing '.com'), but none was thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test Invalid Email two: PASSED");
        }
    }

    @Test
    public void testPasswordMismatch() {
        try {
            UserValidator.validate("ValidUser", "user@example.com", "Password123", "Password321");
            fail("Expected IllegalArgumentException for mismatched passwords, but none was thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test Password Mismatch: PASSED");
        }
    }
}
