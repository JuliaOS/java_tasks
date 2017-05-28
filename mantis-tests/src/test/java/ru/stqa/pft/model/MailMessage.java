package ru.stqa.pft.model;

/**
 * Created by Julia on 5/28/2017.
 */
public class MailMessage {
    public String to;
    public String text;

    public MailMessage(String to, String text){
        this.to = to;
        this.text = text;
    }

}
