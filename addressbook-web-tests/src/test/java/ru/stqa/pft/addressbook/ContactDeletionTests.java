package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() {
        gotoHomePage();
        selectContact();
        deleteSelectedContacts();
        gotoHomePage();
    }

}
