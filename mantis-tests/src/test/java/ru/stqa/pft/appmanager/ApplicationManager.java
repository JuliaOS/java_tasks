package ru.stqa.pft.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Julia on 4/16/2017.
 */
public class ApplicationManager {

    private WebDriver wd;
    private String browser;
    private Properties properties;
    private RegistrationHelper registrationHelper;
    private FTPHelper ftpHelper;
    private MailHelper mailHelper;
    private PasswordChangeHelper passwordChangeHelper;
    private DbHelper db;


    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() throws IOException {
        properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if(wd != null) {
            wd.quit();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public HttpSession newSession(){
        return new HttpSession(this);
    }

    public RegistrationHelper registration() {
        if(registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FTPHelper ftp() {
        if(ftpHelper == null) {
            ftpHelper = new FTPHelper(this);
        }
        return ftpHelper;
    }

    public MailHelper email() {
        if(mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public PasswordChangeHelper changePassword(){
        if(passwordChangeHelper == null) {
            passwordChangeHelper = new PasswordChangeHelper(this);
        }
        return passwordChangeHelper;
    }

    public DbHelper db(){
        if(db == null) {
            db = new DbHelper();
        }
        return db;
    }

    public WebDriver getDriver() {
        if(wd == null){
            if (browser.equals(BrowserType.FIREFOX)){
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)){
                wd = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)){
                wd = new InternetExplorerDriver();

            }
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            String property = properties.getProperty("web.BaseUrl");
            wd.get(properties.getProperty("web.BaseUrl"));
        }
        return wd;
    }
}
