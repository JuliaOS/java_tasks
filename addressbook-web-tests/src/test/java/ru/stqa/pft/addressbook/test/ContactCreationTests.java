package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToAddNewPage();
        app.getContactHelper().createContact(new ContactData("FName", "LName", "UserAddress",
                "+79879999999", "88314444444",
                "fname@yandex.ru", null, "fname@gmail.com"));
        app.getNavigationHelper().goToHomePage();
    }

}
