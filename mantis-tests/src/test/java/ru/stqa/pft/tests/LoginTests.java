package ru.stqa.pft.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by Julia on 5/28/2017.
 */
public class LoginTests extends TestBase {

    @Test

    public void loginTests() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
