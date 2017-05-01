package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {
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
        app.getContactHelper().deleteSelectedContacts();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(index);
        Assert.assertEquals(before, after);
    }

}
