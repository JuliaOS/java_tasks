package ru.stqa.pft.addressbook.appmanager;
import com.solidfire.gson.JsonArray;
import com.solidfire.gson.JsonElement;
import com.solidfire.gson.JsonObject;
import com.solidfire.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Executor;

import java.io.IOException;


/**
 * Created by Julia on 6/3/2017.
 */
public class RestHelper {

    private final ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }


    public boolean issueStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%s.json",issueId )))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonObject jobject = parsed.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("issues");
        jobject = jarray.get(0).getAsJsonObject();
        String result = jobject.get("state_name").toString();
        if(result.equals("Closed")){
            return false;
        }else{
            return true;
        }
    }

    private Executor getExecutor(){
        return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
    }
}
