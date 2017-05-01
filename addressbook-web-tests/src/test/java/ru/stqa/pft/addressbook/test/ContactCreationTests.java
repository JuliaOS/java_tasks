package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToAddNewPage();
        ContactData newContact = new ContactData("FName1", "LName", "UserAddress",
                "+79879999999", "88314444444",
                "fname@yandex.ru", null, "fname@gmail.com");
        app.getContactHelper().createContact(newContact);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() + 1);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        newContact.setId(after.stream().max(byId).get().getId());
        before.add(newContact);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
