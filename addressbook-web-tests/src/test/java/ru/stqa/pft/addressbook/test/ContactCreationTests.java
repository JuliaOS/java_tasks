package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.gotoAddNewPage();
        app.fillNewContactForm(new ContactData("FName", "LName", "UserAddress", "+79879999999", "88314444444", "fname@yandex.ru", "fname@yahoo.com", "fname@gmail.com"));
        app.submitContactCreation();
        app.returnToHomePage();
    }

}
