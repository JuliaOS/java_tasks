package ru.stqa.pft.addressbook.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase{

    @DataProvider
    public Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> groupList = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
        String line = reader.readLine();
        while(line != null){
            String[] split = line.split(";");
            GroupData group = new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2]);
            groupList.add(new Object[] {group});
            line = reader.readLine();
        }
        return groupList.iterator();
    }

    @Test (dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.group().all();
        app.group().create(group);

        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.group().all();
        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(group);
        assertThat(after, equalTo(before.withAdded(group)));
    }

    @Test
    public void testFileCreation() {
        System.out.println(new File(".").getAbsolutePath());
    }

}
