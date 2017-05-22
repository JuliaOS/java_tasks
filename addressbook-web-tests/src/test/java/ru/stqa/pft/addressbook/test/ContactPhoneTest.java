package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Julia on 5/12/2017.
 */
public class ContactPhoneTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditins() {
        app.goTo().homePage();
        if (app.db().contact().size() == 0) {
            app.goTo().addNewPage();
            app.contact().create(new ContactData()
                    .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                    .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com"));
        }
    }

    @Test
    public void testContactPhone() {
        Contacts contacts = app.db().contact();
        ContactData contactFromMainPage = contacts.iterator().next();
        ContactData contactFromEditPage = app.contact().contactFromEditPage(contactFromMainPage);
        app.goTo().homePage();
        assertThat(app.contact().getAllPhones(contactFromMainPage.getId()), equalTo(mergePhones(contactFromEditPage)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String string){
        return string.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}