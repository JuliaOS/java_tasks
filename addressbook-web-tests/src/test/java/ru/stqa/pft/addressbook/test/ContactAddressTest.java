package ru.stqa.pft.addressbook.test;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by Julia on 5/12/2017.
 */
public class ContactAddressTest extends TestBase{
    @BeforeMethod
    public void ensurePreconditins(){
        app.goTo().homePage();
        if( app.db().contact().size() == 0 ){
            app.goTo().addNewPage();
            app.contact().create(new ContactData()
                    .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                    .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com"));
        }
    }

    @Test
    public void testContactAddress(){
        Contacts contacts = app.db().contact();
        ContactData contactFromMainPage = contacts.iterator().next();
        ContactData contactFromEditPage = app.contact().contactFromEditPage(contactFromMainPage);
        assertThat(contactFromMainPage.getAddress(), equalTo(contactFromEditPage.getAddress()));
    }
}
