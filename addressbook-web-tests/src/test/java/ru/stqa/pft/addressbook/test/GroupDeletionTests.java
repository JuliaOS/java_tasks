package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase{

    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().goToGroupPage();
        GroupData group = new GroupData(null, "test1", null, "test3");
        if(! app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(group);
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;
        app.getGroupHelper().selectGroup(index);
        app.getGroupHelper().deleteSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(index);
        Assert.assertEquals(before, after);
    }

}
