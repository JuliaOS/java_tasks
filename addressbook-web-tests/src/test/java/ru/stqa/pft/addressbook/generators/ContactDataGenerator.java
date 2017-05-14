package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 5/14/2017.
 */
public class ContactDataGenerator {
    public static void main (String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);
        List<ContactData> contacts = generateContacts(count);
        saveToCsv(contacts, file);

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
