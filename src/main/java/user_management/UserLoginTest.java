package user_management;

import org.junit.Test;

public class UserLoginTest {
    @Test
    public void testAuthentication() {
        try {
            UserLogin login = new UserLogin();
            String result = login.authenticate("deshan", "12345678");

            if (result == null) {
                System.out.println("testAuthentication: PASSED");
            } else {
                System.out.println("testAuthentication: FAILED");
                System.out.println("Error: " + result);
            }
        } catch (Exception e) {
            System.out.println("testAuthentication: FAILED");
            e.printStackTrace();
        }
    }
}
