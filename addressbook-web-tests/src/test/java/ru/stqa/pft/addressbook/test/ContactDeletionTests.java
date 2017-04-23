package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();
        if(! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoAddNewPage();
            app.getContactHelper().createContact(new ContactData("FName", "LName", "UserAddress",
                    "+79879999999", "88314444444",
                    "fname@yandex.ru", null, "fname@gmail.com"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getNavigationHelper().gotoHomePage();
    }

}
