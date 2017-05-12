package ru.stqa.pft.addressbook.appmanager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.List;

/**
 * Created by Julia on 4/16/2017.
 */
public class ContactHelper extends HelperBase{
    public Contacts contactCache = null;

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

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactModification(int id) {
        wd.findElement(By.xpath(String.format("//input[@id='%s']/../../td[8]/a",id))).click();
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
        contactCache = null;
        submitContactCreation();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact.getId());
        fillNewContactForm(contact);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void deleteContact(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        contactCache = null;
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count(){
        return wd.findElements(By.cssSelector("tr[name = 'entry']")).size();
    }

    public Contacts all() {
        if(contactCache != null){
            return contactCache;
        }
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name = 'entry']"));
        contactCache = new Contacts();
        for(WebElement e : elements){
            int id = Integer.parseInt(e.findElement(By.tagName("input")).getAttribute("id"));
            String lname = e.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String fname = e.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String address = e.findElement(By.cssSelector("td:nth-child(4)")).getText();
            String allEmails = e.findElement(By.cssSelector("td:nth-child(5)")).getText();
            String allPhones = e.findElement(By.cssSelector("td:nth-child(6)")).getText();
            ContactData newContact = new ContactData().withId(id).withFirstName(fname).withLastName(lname)
                    .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones);
            contactCache.add(newContact);
        }
        return contactCache;
    }


    public ContactData contactFromEditPage(ContactData contact) {
        initContactModification(contact.getId());
        String firstname = wd.findElement(By.cssSelector("input[name='firstname']")).getAttribute("value");
        String lastname = wd.findElement(By.cssSelector("input[name='lastname']")).getAttribute("value");
        String address = wd.findElement(By.cssSelector("textarea[name='address']")).getAttribute("value");
        String email1 = wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
        String email2 = wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
        String email3 = wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");
        String homePhone = wd.findElement(By.cssSelector("input[name='home']")).getAttribute("value");
        String mobilePhone = wd.findElement(By.cssSelector("input[name='mobile']")).getAttribute("value");
        String workPhone = wd.findElement(By.cssSelector("input[name='work']")).getAttribute("value");
        return new ContactData().withFirstName(firstname).withLastName(lastname).withAddress(address)
                .withEmail1(email1).withEmail2(email2).withEmail3(email3)
                .withHomePhone(homePhone).withWorkPhone(workPhone).withMobilePhone(mobilePhone);
    }

}
