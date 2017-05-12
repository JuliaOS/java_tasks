package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Julia on 4/16/2017.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditins(){
        app.goTo().homePage();
        if( app.contact().list().size() == 0 ){
            app.goTo().addNewPage();
            app.contact().create(new ContactData()
                    .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                    .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification(){
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData newContact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("FName4").withLastName("LName4")
                .withAddress("UserAddress").withMobilePhone("+79879999999").withWorkPhone("88314444444")
                .withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com");
        app.contact().modify(newContact);
        Set<ContactData> after = app.contact().all();

        assertThat(after.size(), equalTo(before.size()));
        before.remove(modifiedContact);
        before.add(newContact);
        assertThat(after, equalTo(before));
    }
}
