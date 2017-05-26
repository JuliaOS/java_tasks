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
 * Created by yuos1116 on 5/26/2017.
 */
public class ContactAddingToGroupTest extends TestBase{

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
   public void testContactAdditionToGroup() {
       Contacts before = app.db().contact();
       ContactData contactBeforeAddition = before.iterator().next();
       ContactData contactAfterAddition = addToGroup(contactBeforeAddition);
       app.goTo().homePage();
       Contacts after = app.db().contact();
       assertThat(after, equalTo(before.without(contactBeforeAddition).withAdded(contactAfterAddition)));
       //GroupData groupForDeletion = intersect(contactBeforeAddition.getGroups(), contactAfterAddition.getGroups()).iterator().next();
       //app.goTo().groupViewPage(groupForDeletion);
       //verifyContactsInGroupListInUI(groupForDeletion);
   }

    private ContactData addToGroup(ContactData contact) {
        ContactData addedContact = new ContactData(contact);
        Groups groups = app.db().group();
        GroupData groupForAdding;

        if (addedContact.getGroups().size() == 0) {
            groupForAdding = groups.iterator().next();
        } else {
            if (addedContact.getGroups().size() == groups.size()) {
                app.goTo().groupPage();
                groupForAdding = new GroupData().withName("addedGroup").withHeader("header").withFooter("footer");
                app.group().create(groupForAdding);
                Groups groupsUpdated = app.db().group();
                groupForAdding.withId(groupsUpdated.stream().mapToInt((g) -> g.getId()).max().getAsInt());
                app.goTo().homePage();
            } else {
                Groups intersectSet = new Groups();
                for (GroupData g : groups) {
                    if (! addedContact.getGroups().contains(g)) {
                        intersectSet.add(g);
                    }
                }
                groupForAdding = intersectSet.iterator().next();
            }
        }
            app.contact().addContactToGroup(addedContact, groupForAdding);
            addedContact.inGroup(groupForAdding);
            return addedContact;
        }

    /*private Groups intersect (Groups groups1, Groups groups2){
        Groups intersectSet = new Groups();
        for (GroupData g : groups2) {
            if (! groups1.contains(g)) {
                intersectSet.add(g);
            }
        }
        return intersectSet;
   }*/

}
