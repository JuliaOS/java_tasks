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
        contactFromMainPage.withAllPhones(newSmth(contactFromMainPage));
        //assertThat(mergePersonalInfo(contactFromMainPage), equalTo(cleanedData(contactFromPersonalInfo.getPersonalInfo())));
        assertThat(mergePersonalInfo(contactFromMainPage), equalTo(contactFromPersonalInfo.getPersonalInfo().replaceAll("\n\n", "\n")));
    }

    private String mergePersonalInfo(ContactData contact) {
        String fullname = Arrays.asList(contact.getFirstName(), contact.getLastName())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(" "));
        return Arrays.asList(fullname,
                contact.getAddress(),
                contact.getAllPhones(), contact.getAllEmails())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String cleanedData(String data){
        return data.replaceAll("[-()]", "")
                .replaceAll("H: ","").replaceAll("M: ", "").replaceAll("W: ", "")
                .replaceAll(" ", "").replaceAll("\n\n", "\n");
    }

    public String newSmth(ContactData contact){
        app.goTo().homePage();
        ContactData contactFromEditPage = app.contact().contactFromEditPage(contact);
        String allPhones = Arrays.asList(contactFromEditPage.getHomePhone(), contactFromEditPage.getMobilePhone(), contactFromEditPage.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
        assertThat(allPhones, equalTo(contact.getAllPhones()));
        String homePhone = "";
        String mobilePhone = "";
        String workPhone = "";
        if(!contactFromEditPage.getHomePhone().equals("")){
            homePhone = "H: " + contactFromEditPage.getHomePhone();
        }
        if(!contactFromEditPage.getMobilePhone().equals("")){
            mobilePhone = "M: " + contactFromEditPage.getMobilePhone();
        }
        if(!contactFromEditPage.getWorkPhone().equals("")){
            mobilePhone = "W: " + contactFromEditPage.getWorkPhone();
        }

        return Arrays.asList(homePhone, mobilePhone, workPhone)
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String string){
        return string.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}
