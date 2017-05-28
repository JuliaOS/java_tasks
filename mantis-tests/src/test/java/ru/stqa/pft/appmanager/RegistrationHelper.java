package ru.stqa.pft.appmanager;

import org.openqa.selenium.WebDriver;

/**
 * Created by Julia on 5/28/2017.
 */
public class RegistrationHelper {
    private ApplicationManager app;
    private WebDriver wd;

    public RegistrationHelper(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.BaseUrl") + "/signup_page.php");
    }
}
