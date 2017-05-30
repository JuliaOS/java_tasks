package ru.stqa.pft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.appmanager.HttpSession;
import ru.stqa.pft.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;
import static ru.stqa.pft.tests.TestBase.app;

/**
 * Created by Julia on 5/29/2017.
 */
public class PasswordChangeTest extends TestBase{

    @BeforeMethod
    public void startMailServer(){
        app.email().start();
    }

    @Test
    public void changePasswordTest() throws IOException {

        String  newPassword = "password1";
        String username = "user1496153992967";
        app.changePassword().start(username);
        List<MailMessage> mailMessages = app.email().waitForMail(1, 20000);
        String confirmationLink = app.changePassword().findConfirmationLink(mailMessages, "user1496153992967@localhost.localdomain");
        app.changePassword().finish(confirmationLink, newPassword);
        HttpSession session = app.newSession();
        assertTrue(session.login(username, newPassword));
        assertTrue(session.isLoggedInAs(username));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.email().stop();
    }
}
