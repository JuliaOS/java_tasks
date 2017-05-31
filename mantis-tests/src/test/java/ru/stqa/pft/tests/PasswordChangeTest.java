package ru.stqa.pft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.appmanager.HttpSession;
import ru.stqa.pft.model.MailMessage;
import ru.stqa.pft.model.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.AssertJUnit.assertTrue;
import static ru.stqa.pft.tests.TestBase.app;

/**
 * Created by Julia on 5/29/2017.
 */
public class PasswordChangeTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.email().start();
        assertTrue("No available users in the database", app.db().user().size() > 1);
    }

    @Test
    public void changePasswordTest() throws IOException {

        String  newPassword = "password1";
        ArrayList<Users> users = app.db().user();
        int randomNum = ThreadLocalRandom.current().nextInt(1, users.size());
        Users userForUpdate = users.get(randomNum);
        app.changePassword().start(userForUpdate.getUsername());
        List<MailMessage> mailMessages = app.email().waitForMail(1, 20000);
        String confirmationLink = app.changePassword().findConfirmationLink(mailMessages, userForUpdate.getEmail());
        app.changePassword().finish(confirmationLink, newPassword);
        HttpSession session = app.newSession();
        assertTrue(session.login(userForUpdate.getUsername(), newPassword));
        assertTrue(session.isLoggedInAs(userForUpdate.getUsername()));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.email().stop();
    }
}
