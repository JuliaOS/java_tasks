package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {
        app.gotoHomePage();
        app.selectContact();
        app.deleteSelectedContacts();
        app.gotoHomePage();
    }

}
