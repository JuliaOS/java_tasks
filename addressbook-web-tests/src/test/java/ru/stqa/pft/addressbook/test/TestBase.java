package ru.stqa.pft.addressbook.test;

import org.hibernate.service.spi.ServiceException;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by Julia on 4/16/2017.
 */
public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite (alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p){
        logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod (alwaysRun = true)
    public void logTestStop(Method m){
        logger.info("Stop test " + m.getName());
    }


    public void verifyGroupsListInUI() {
        if(Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().group();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }

    public void verifyContactsListInUI() {
        if(Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contact();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts.stream().map((f) -> new ContactData().withId(f.getId()).withFirstName(f.getFirstName()).withLastName(f.getLastName())
                            .withAddress(f.getAddress())).collect(Collectors.toSet()),
                    equalTo(dbContacts.stream().map((g) -> new ContactData().withId(g.getId()).withFirstName(g.getFirstName()).withLastName(g.getLastName())
                            .withAddress(g.getAddress())).collect(Collectors.toSet())));
        }
    }

    public void verifyContactsInGroupListInUI(GroupData group) {
        if(Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contact();
            Contacts groupContacts = new Contacts();
            for(ContactData contact : dbContacts){
                if(contact.getGroups().contains(group)){
                    groupContacts.add(contact);
                }
            }
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts.stream().map((f) -> new ContactData().withId(f.getId()).withFirstName(f.getFirstName()).withLastName(f.getLastName())
                            .withAddress(f.getAddress())).collect(Collectors.toSet()),
                    equalTo(groupContacts.stream().map((g) -> new ContactData().withId(g.getId()).withFirstName(g.getFirstName()).withLastName(g.getLastName())
                            .withAddress(g.getAddress())).collect(Collectors.toSet())));
        }
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException, ServiceException {
        return app.rest().isStatusOpen(issueId);
    }

}
