package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(newContact);
        Assert.assertEquals(before, after);
    }
}
