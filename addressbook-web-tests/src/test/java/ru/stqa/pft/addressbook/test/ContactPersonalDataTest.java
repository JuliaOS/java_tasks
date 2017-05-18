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
public class ContactPersonalDataTest extends TestBase {
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
    public void testPersonalInfo() {
        Contacts contacts = app.contact().all();
        ContactData contactFromMainPage = contacts.iterator().next();
        ContactData contactFromPersonalInfo = app.contact().contactFromPersonalInfoPage(contactFromMainPage);
        assertThat(mergePersonalInfo(contactFromMainPage), equalTo(cleanedPersonalInfo(contactFromPersonalInfo.getPersonalInfo())));
    }

    private String mergePersonalInfo(ContactData contact) {
        String fullname = Arrays.asList(contact.getFirstName(), contact.getLastName())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(" "));
        String convertedPhones = convertPhonesFor(contact);
        return Arrays.asList(fullname,
                contact.getAddress(),
                convertedPhones, contact.getAllEmails())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String convertPhonesFor(ContactData contact){
        app.goTo().homePage();
        ContactData contactFromEditPage = app.contact().contactFromEditPage(contact);
        String allPhones = Arrays.asList(contactFromEditPage.getHomePhone(), contactFromEditPage.getMobilePhone(), contactFromEditPage.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPersonalDataTest::cleanedPhones)
                .collect(Collectors.joining("\n"));
        assertThat(allPhones, equalTo(contact.getAllPhones()));
        String homePhone = contactFromEditPage.getHomePhone();
        String mobilePhone = contactFromEditPage.getMobilePhone();
        String workPhone = contactFromEditPage.getWorkPhone();
        if(!homePhone.equals("")){
            homePhone = "H: " + homePhone;
        }
        if(!mobilePhone.equals("")){
            mobilePhone = "M: " + mobilePhone;
        }
        if(!workPhone.equals("")){
            workPhone = "W: " + workPhone;
        }

        return Arrays.asList(homePhone, mobilePhone, workPhone)
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String cleanedPhones(String string){
        return string.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
    public String cleanedPersonalInfo(String string){
        return string.replaceAll("\n\n", "\n");
    }
}
