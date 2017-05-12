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
public class ContactEmailTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditins() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.goTo().addNewPage();
            app.contact().create(new ContactData()
                    .withFirstName("FName1").withLastName("LName").withAddress("UserAddress").withMobilePhone("+79879999999")
                    .withWorkPhone("88314444444").withEmail1("fname@yandex.ru").withEmail3("fname@gmail.com"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactEmail() {
        Contacts contacts = app.contact().all();
        ContactData contactFromMainPage = contacts.iterator().next();
        ContactData contactFromEditPage = app.contact().contactFromEditPage(contactFromMainPage);
        assertThat(contactFromMainPage.getAllEmails(), equalTo(mergeEmails(contactFromEditPage)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals("")).collect(Collectors.joining("\n"));
    }


}