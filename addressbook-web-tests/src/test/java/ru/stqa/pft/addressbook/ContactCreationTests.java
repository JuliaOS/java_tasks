package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        gotoAddNewPage();
        fillNewContactForm(new ContactData("FName", "LName", "UserAddress", "+79879999999", "88314444444", "fname@yandex.ru", "fname@yahoo.com", "fname@gmail.com"));
        submitContactCreation();
        returnToHomePage();
    }

}
