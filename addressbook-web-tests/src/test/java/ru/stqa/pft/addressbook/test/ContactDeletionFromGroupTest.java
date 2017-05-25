package ru.stqa.pft.addressbook.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by yuos1116 on 5/25/2017.
 */
public class ContactDeletionFromGroupTest extends TestBase{
    @BeforeMethod
    public void ensurePreconditins(){
        if( app.db().group().size() == 0 ){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if( app.db().contact().size() == 0 ){
            app.goTo().addNewPage();
            app.contact().create(new ContactData().withFirstName("fname1").withLastName("lname1"));
        }

    }

    @Test
    public void testContactDeletionFromGroup() {
        Contacts before = app.db().contact();
        Groups groups = app.db().group();
        ContactData contactForDeletion = before.iterator().next();
        ContactData removedContact = new ContactData(contactForDeletion);


        GroupData groupForDeletion;

        app.goTo().homePage();

        if(removedContact.getGroups().size() == 0){
            groupForDeletion = groups.iterator().next();
            app.contact().addContactToGroup(removedContact, groupForDeletion);
            removedContact.inGroup(groupForDeletion);
            app.goTo().homePage();
        }
        else{
            groupForDeletion = removedContact.getGroups().iterator().next();
        }

        app.contact().removeContactFromGroup(removedContact, groupForDeletion);

        removedContact.outOfGroup(groupForDeletion);
        //app.goTo().homePage();
        Contacts after = app.db().contact();
        //Assert.assertTrue(after.contains(contactForDeletion));
        Contacts test = before.without(contactForDeletion).withAdded(removedContact);
        assertThat(after, equalTo(test));
        //verifyContactsInGroupListInUI(groupForDeletion);
    }




}
