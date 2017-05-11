package ru.stqa.pft.addressbook.appmanager;

import javafx.scene.effect.SepiaTone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 4/16/2017.
 */
public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillNewContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstName());
        type(By.name("lastname"),contactData.getLastName());
        type(By.name("address"),contactData.getAddress());
        type(By.name("mobile"),contactData.getMobilePhone());
        type(By.name("work"),contactData.getWorkPhone());
        type(By.name("email"),contactData.getEmail1());
        type(By.name("email2"),contactData.getEmail2());
        type(By.name("email3"),contactData.getEmail3());
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactModification(int id) {
        wd.findElement(By.xpath("//input[@id=\'" + id + "\']/../../td[8]")).click();
     }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
        wd.switchTo().alert().accept();
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void create(ContactData contactData) {
        fillNewContactForm(contactData);
        submitContactCreation();
    }

    public void modify(int index, ContactData newContact) {
        selectContact(index);
        initContactModification(index);
        fillNewContactForm(newContact);
        submitContactModification();
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact.getId());
        fillNewContactForm(contact);
        submitContactModification();
        returnToHomePage();
    }

    public void deleteContact(int index) {
        selectContact(index);
        deleteSelectedContacts();
        returnToHomePage();
    }

    public void deleteContact(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        returnToHomePage();
    }



    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> list() {
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        List<ContactData> contacts = new ArrayList<ContactData>();
        for(WebElement e : elements){
            int id = Integer.parseInt(e.findElement(By.tagName("input")).getAttribute("id"));
            String lname = e.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String fname = e.findElement(By.cssSelector("td:nth-child(3)")).getText();
            ContactData newContact = new ContactData().withId(id).withFirstName(fname).withLastName(lname);
            contacts.add(newContact);
        }
        return contacts;
    }

    public Set<ContactData> all() {
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        Set<ContactData> contacts = new HashSet<ContactData>();
        for(WebElement e : elements){
            int id = Integer.parseInt(e.findElement(By.tagName("input")).getAttribute("id"));
            String lname = e.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String fname = e.findElement(By.cssSelector("td:nth-child(3)")).getText();
            ContactData newContact = new ContactData().withId(id).withFirstName(fname).withLastName(lname);
            contacts.add(newContact);
        }
        return contacts;
    }


}
