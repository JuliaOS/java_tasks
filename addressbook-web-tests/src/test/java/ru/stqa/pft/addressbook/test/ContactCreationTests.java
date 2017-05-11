package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        Set<ContactData> before = app.contact().all();
        app.goTo().addNewPage();
        ContactData newContact = new ContactData()
                .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com");
        app.contact().create(newContact);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().all();

        Assert.assertEquals(after.size(), before.size() + 1);

        newContact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(newContact);
        Assert.assertEquals(before, after);
    }

}
