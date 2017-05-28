package ru.stqa.pft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.model.MailMessage;

import java.util.List;

/**
 * Created by Julia on 5/28/2017.
 */
public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer(){
        app.email().start();
    }

    @Test

    public void testRegistration(){
        String email = "user1@localhost.localdomain";
        String username = "user1";
        app.registration().start(username, email);
        List<MailMessage> mailMessages = app.email().waitForMail(2, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, "password");


    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.email().stop();
    }
}
