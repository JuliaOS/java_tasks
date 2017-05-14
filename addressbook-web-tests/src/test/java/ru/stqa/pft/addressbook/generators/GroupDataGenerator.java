package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static sun.tools.jar.CommandLine.parse;

/**
 * Created by Julia on 5/14/2017.
 */
public class GroupDataGenerator {
    @Parameter(names = "-c", description = "Groups count")
    public int count;
    @Parameter(names = "-f", description = "Target file")
    private String file = new String();

    public static void main (String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jcommander = JCommander.newBuilder()
                .addObject(generator)
                .build();
        try {
            jcommander.parse(args);
        } catch (ParameterException ex){
            jcommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroups(count);
        saveToCsv(groups, new File(file));
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<GroupData>();
        for(int i = 0; i < count; i++){
            GroupData newgroup = new GroupData();
            groups.add(newgroup.withName(String.format("name%s", i))
                    .withHeader(String.format("header%s", i))
                    .withFooter(String.format("footer%s", i)));
        }
        return groups;
    }

    private void saveToCsv(List<GroupData> groups, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for(GroupData group : groups){
            writer.write(String.format("%s;%s;%s\n",group.getName(), group.getHeader(), group.getFooter() ));
        }
        writer.close();
    }
}
