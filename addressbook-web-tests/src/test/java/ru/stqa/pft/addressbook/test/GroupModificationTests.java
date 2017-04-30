package ru.stqa.pft.addressbook.test;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;


/**
 * Created by Julia on 4/16/2017.
 */
public class GroupModificationTests extends TestBase{

    @Test
    public void testGroupModification(){
        app.getNavigationHelper().goToGroupPage();
        GroupData group = new GroupData(null, "test1", null, "test3");
        if(! app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(group);
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;
        app.getGroupHelper().selectGroup(index);
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(group);
        Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
        //Assert.assertEquals(before, after);
    }

}
