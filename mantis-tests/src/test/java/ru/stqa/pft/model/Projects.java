package ru.stqa.pft.model;

/**
 * Created by Julia on 6/3/2017.
 */
public class Projects {

    private int id;
    private String name;



    public Projects withId(int id) {
        this.id = id;
        return this;
    }

    public Projects withName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;

    }
}
