package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
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
public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file = new String();

    @Parameter(names = "-d", description = "Data format")
    public String format = new String();

    public static void main (String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jcommander = JCommander.newBuilder()
                .addObject(generator)
                .build();
        try{
            jcommander.parse(args);
        }catch(ParameterException ex){
            jcommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if(format.equals("csv")){
            saveToCsv(contacts, new File(file));
        } else if(format.equals("json")){
            saveToJson(contacts, new File(file));
        } else{
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveToJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for(int i = 0; i < count; i++){
            ContactData newcontact = new ContactData();
            contacts.add(newcontact.withFirstName(String.format("fname%s", i))
                    .withLastName(String.format("lname%s", i))
                    .withAddress(String.format("address%s", i))
                    .withHomePhone(String.format("home_phone%s", i))
                    .withEmail1(String.format("e-mail1 %s", i)));
        }
        return contacts;
    }

    private static void saveToCsv(List<ContactData> contacts, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for(ContactData contact : contacts){
            writer.write(String.format("%s;%s;%s;%s;%s\n",contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                    contact.getHomePhone(), contact.getEmail1()));
        }
        writer.close();
    }
}
