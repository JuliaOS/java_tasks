package ru.stqa.pft.model;

/**
 * Created by Julia on 6/3/2017.
 */
public class Issues {
    private int id;
    private String  summary;
    private String description;
    Projects project;


    public Issues withId(int id) {
        this.id = id;
        return this;
    }

    public Issues withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Issues withDescription(String description) {
        this.description = description;
        return this;
    }

    public Issues withProject(Projects project) {
        this.project = project;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Projects getProject() {
        return project;
    }
}
