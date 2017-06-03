package ru.stqa.pft.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.model.Issues;
import ru.stqa.pft.model.Projects;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Julia on 6/3/2017.
 */
public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {

        Set<Projects> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for(Projects p : projects) {
            System.out.println(String.format("Project name = %s",p.getName()));
        }
    }
    @Test
    public void testAddIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Projects> projects = app.soap().getProjects();
        Issues newIssue = new Issues().withSummary("test issue summary")
                .withDescription("test issue description").withProject(projects.iterator().next());
        Issues addedIssue = app.soap().addIssue(newIssue);
        assertEquals(newIssue.getSummary(), addedIssue.getSummary());


    }
}
