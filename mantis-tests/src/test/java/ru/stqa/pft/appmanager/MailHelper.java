package ru.stqa.pft.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Julia on 5/28/2017.
 */
public class MailHelper {
    private ApplicationManager app;
    private Wiser wiser;

    public MailHelper(ApplicationManager app) {
        this.app = app;
        this.wiser = new Wiser();
    }

    public List<MailMessage> waitForMail(int count, long timeout){
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() < start + timeout){
            if(wiser.getMessages().size() >= count){
                return wiser.getMessages().stream().map((g)-> toModeMail(g)).collect(Collectors.toList());
            }
            try{
                Thread.sleep(1000);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        throw new Error("No e-mail");
    }

    public static MailMessage toModeMail(WiserMessage email) {
        try{
            MimeMessage m = email.getMimeMessage();
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start(){
        wiser.start();
    }

    public void stop(){
        wiser.stop();
    }

}
