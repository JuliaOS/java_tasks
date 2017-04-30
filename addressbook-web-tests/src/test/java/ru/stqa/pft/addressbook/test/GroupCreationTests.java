package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().createGroup(new GroupData(null, "test1", null, "test3"));
    }

}
