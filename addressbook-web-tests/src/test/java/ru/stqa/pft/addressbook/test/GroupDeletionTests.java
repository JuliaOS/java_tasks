package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditins(){
        app.goTo().groupPage();
        if( app.db().group().size() == 0 ){
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletion() {
            Groups before = app.db().group();
            GroupData deletedGroup = before.iterator().next();
            app.group().delete(deletedGroup);
            assertThat(app.group().count(), equalTo(before.size() - 1));
            Groups after = app.db().group();
            assertThat(after, equalTo(before.without(deletedGroup)));
            verifyGroupsListInUI();
    }

}
