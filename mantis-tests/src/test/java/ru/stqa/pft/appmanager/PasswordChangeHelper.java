package ru.stqa.pft.appmanager;

import org.openqa.selenium.By;


/**
 * Created by Julia on 5/29/2017.
 */
public class PasswordChangeHelper extends HelperBase{

    public PasswordChangeHelper(ApplicationManager app) {
        super(app);
    }

    public void start(){
        wd.get(app.getProperty("web.BaseUrl") + "/login_page.php");
        type(By.name("username"), app.getProperty("web.adminLogin"));
        click(By.xpath("//input[@value='Login']"));
        type(By.name("password"), app.getProperty("web.adminPassword"));
        click(By.xpath("//input[@value='Login']"));
        wd.get(app.getProperty("web.BaseUrl") + "/manage_user_page.php");
        click(By.linkText("user1496077699077"));
        click(By.xpath("//input[@value='Reset Password']"));


    }
}
