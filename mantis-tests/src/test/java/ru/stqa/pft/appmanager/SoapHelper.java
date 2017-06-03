package ru.stqa.pft.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.model.Issues;
import ru.stqa.pft.model.Projects;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Julia on 6/3/2017.
 */
public class SoapHelper {

    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
         this.app = app;
    }


    public Set<Projects> getProjects() throws ServiceException, MalformedURLException, RemoteException {

        MantisConnectPortType mcPort = getMantisConnectPort();
        ProjectData[] mcProjects = mcPort.mc_projects_get_user_accessible("administrator", "root");
        return Arrays.asList(mcProjects).stream().map((g) -> new Projects().withId(g.getId().intValue()).withName(g.getName()))
                .collect(Collectors.toSet());
    }

    private MantisConnectPortType getMantisConnectPort() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator()
                    .getMantisConnectPort(new URL("http://localhost/mantisbt-2.4.1/api/soap/mantisconnect.php"));
    }

    public Issues addIssue(Issues issue) throws MalformedURLException, ServiceException, RemoteException {

        MantisConnectPortType mcPort = getMantisConnectPort();
        String[] categories = mcPort.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
        IssueData newIssue = new IssueData();
        newIssue.setSummary(issue.getSummary());
        newIssue.setDescription(issue.getDescription());
        newIssue.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        newIssue.setCategory(categories[0]);
        BigInteger id = mcPort.mc_issue_add("administrator", "root", newIssue);
        IssueData createdIssue = mcPort.mc_issue_get("administrator", "root", id);
        return new Issues().withId(id.intValue()).withSummary(createdIssue.getSummary()).withDescription(createdIssue.getDescription())
                .withProject(new Projects().withId(createdIssue.getProject().getId().intValue())
                .withName(createdIssue.getProject().getName()));
    }
}
