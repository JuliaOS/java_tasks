package ru.stqa.pft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.appmanager.HttpSession;
import ru.stqa.pft.model.MailMessage;
import ru.stqa.pft.model.Users;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Julia on 5/28/2017.
 */
public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer() throws RemoteException, ServiceException, MalformedURLException {
        int issueId = 1;
        skipIfNotFixed(issueId);
        app.email().start();
    }

    @Test

    public void testRegistration() throws IOException {
        long now = System.currentTimeMillis();
        String password = "password";
        Users user = new Users(String.format("user%s", now), String.format("user%s@localhost.localdomain", now));
        app.registration().start(user.getUsername(), user.getEmail());
        List<MailMessage> mailMessages = app.email().waitForMail(2, 10000);
        String confirmationLink = app.registration().findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, password);
        HttpSession session = app.newSession();
        assertTrue(session.login(user.getUsername(), password));
        assertTrue(session.isLoggedInAs(user.getUsername()));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.email().stop();
    }
}
