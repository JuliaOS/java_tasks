package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        app.goTo().addNewPage();
        ContactData newContact = new ContactData()
                .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com");
        app.contact().create(newContact);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(after.size(), before.size() + 1);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
        newContact.withId(after.stream().max(byId).get().getId());
        before.add(newContact);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
