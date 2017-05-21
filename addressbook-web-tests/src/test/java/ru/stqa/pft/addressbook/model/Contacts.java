package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 5/12/2017.
 */
public class Contacts extends ForwardingSet<ContactData> {
    private Set<ContactData> contactDelegate;

    public Contacts() {
        contactDelegate = new HashSet<ContactData>();
    }

    public Contacts(Contacts contactSet) {
        contactDelegate = new HashSet<ContactData>(contactSet.delegate());
    }

    public Contacts(Collection<ContactData> contact) {
        contactDelegate = new HashSet<ContactData>(contact);
    }

    @Override
    protected Set<ContactData> delegate() {
        return contactDelegate;
    }

    public Contacts withAdded(ContactData contact){
        Contacts newSet = new Contacts(this);
        newSet.add(contact);
        return newSet;
    }

    public Contacts without(ContactData contact){
        Contacts newSet = new Contacts(this);
        newSet.remove(contact);
        return newSet;
    }
}
