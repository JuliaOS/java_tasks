package ru.stqa.pft.addressbook.test;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;


/**
 * Created by Julia on 4/16/2017.
 */
public class GroupModificationTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditins(){
        app.goTo().groupPage();
        if( app.db().group().size() == 0 ){
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModification(){
        Groups before = app.db().group();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().
                withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");
        app.group().modify(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.db().group();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
        verifyGroupsListInUI();
    }

}
