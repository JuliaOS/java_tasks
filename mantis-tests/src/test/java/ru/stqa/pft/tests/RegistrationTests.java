package ru.stqa.pft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.appmanager.HttpSession;
import ru.stqa.pft.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Julia on 5/28/2017.
 */
public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer(){
        app.email().start();
    }

    @Test

    public void testRegistration() throws IOException {
        long now = System.currentTimeMillis();
        String username = String.format("user%s", now);
        String password = "password";
        String email = String.format("user%s@localhost.localdomain", now);
        app.registration().start(username, email);
        List<MailMessage> mailMessages = app.email().waitForMail(2, 10000);
        String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        HttpSession session = app.newSession();
        assertTrue(session.login(username, password));
        assertTrue(session.isLoggedInAs(username));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.email().stop();
    }
}
