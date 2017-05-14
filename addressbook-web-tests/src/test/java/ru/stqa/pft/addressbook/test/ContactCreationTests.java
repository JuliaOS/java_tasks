package ru.stqa.pft.addressbook.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> contactList= new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();
        while(line != null){
            String[] split = line.split(";");
            ContactData contact = new ContactData().withFirstName(split[0]).withLastName(split[1])
                    .withAddress(split[2]).withHomePhone(split[3]).withEmail1(split[4]);
            contactList.add(new Object[] {contact});
            line = reader.readLine();
        }
        return contactList.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData newContact) {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().addNewPage();
        app.contact().create(newContact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        newContact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(newContact)));
    }

}
