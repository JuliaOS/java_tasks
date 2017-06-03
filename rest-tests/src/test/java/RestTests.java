import com.solidfire.gson.*;
import com.solidfire.gson.reflect.TypeToken;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Julia on 6/3/2017.
 */
public class RestTests {

    @Test
    public void restTest() throws IOException {
        int issueId = 6;
        issueStatus(issueId);
        Set<Issue> oldIssues = getIssues();
        Issue issue = new Issue().withSubject("test_subject").withDescription("test_description");
        int id = addNewIssue(issue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(issue.withId(id));
        assertEquals(oldIssues, newIssues);
    }

    private int addNewIssue(Issue issue) throws IOException {
        String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", issue.getSubject()),
                          new BasicNameValuePair("description", issue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
    }

    private Executor getExecutor(){
        return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
    }

    public boolean issueStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%s.json",issueId )))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonObject jobject = parsed.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("issues");
        jobject = jarray.get(0).getAsJsonObject();
        String result = jobject.get("state_name").toString();
        boolean closed = result.equals("Closed");
        return result.equals("Closed");
    }
}
