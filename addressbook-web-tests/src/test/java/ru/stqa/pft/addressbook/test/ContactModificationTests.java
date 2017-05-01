package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

/**
 * Created by Julia on 4/16/2017.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().goToHomePage();
        if(! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().goToAddNewPage();
            app.getContactHelper().createContact(new ContactData("FName", "LName", "UserAddress",
                    "+79879999999", "88314444444",
                    "fname@yandex.ru", null, "fname@gmail.com"));
            app.getNavigationHelper().goToHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 1;
        app.getContactHelper().selectContact(index);
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillNewContactForm(new ContactData("FName", "LName1", "UserAddress", "+79878888888", "88315555555", "fname1@yandex.ru", "fname1@yahoo.com", "fname1@gmail.com"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();
    }
}
