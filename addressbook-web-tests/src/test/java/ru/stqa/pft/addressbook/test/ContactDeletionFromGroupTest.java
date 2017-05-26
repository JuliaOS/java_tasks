package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
        app.goTo().homePage();
    }

    @Test
    public void testContactDeletionFromGroup() {
        Contacts before = app.db().contact();
        ContactData contactBeforeDeletion = before.iterator().next();
        ContactData contactAfterDeletion = removeFromGroup(contactBeforeDeletion);
        app.goTo().homePage();
        Contacts after = app.db().contact();
        assertThat(after, equalTo(before.without(contactBeforeDeletion).withAdded(contactAfterDeletion)));
        //verifyContactsInGroupListInUI(groupForDeletion);
    }

    private ContactData removeFromGroup(ContactData contact){
        ContactData removedContact = new ContactData(contact);
        GroupData groupForDeletion;

        if(removedContact.getGroups().size() == 0){
            groupForDeletion = app.db().group().iterator().next();
            app.contact().addContactToGroup(removedContact, groupForDeletion);
            removedContact.inGroup(groupForDeletion);
            app.goTo().homePage();
        }
        else{
            groupForDeletion = removedContact.getGroups().iterator().next();
        }

        app.contact().removeContactFromGroup(removedContact, groupForDeletion);
        removedContact.outOfGroup(groupForDeletion);
        return removedContact;
    }
}
