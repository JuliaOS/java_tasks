package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Julia on 5/12/2017.
 */
public class Groups extends ForwardingSet<GroupData> {
    private Set<GroupData> groupDelegate;

    public Groups(Groups groupSet) {
        groupDelegate = new HashSet<GroupData>(groupSet.delegate());
    }

    public Groups() {
        groupDelegate = new HashSet<GroupData>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return groupDelegate;
    }

    public Groups withAdded(GroupData group){
        Groups newSet = new Groups(this);
        newSet.add(group);
        return newSet;

    }

    public Groups without(GroupData group){
        Groups newSet = new Groups(this);
        newSet.remove(group);
        return newSet;
    }
}
